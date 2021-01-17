package ru.marina.contactlistviewermvp.repository

import io.reactivex.Single
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import ru.marina.contactlistviewermvp.CONST.CACHE_UPDATE_TIME
import ru.marina.contactlistviewermvp.data.db.dao.ContactsDao
import ru.marina.contactlistviewermvp.data.mapper.ContactsMapper
import ru.marina.contactlistviewermvp.data.model.Contact
import ru.marina.contactlistviewermvp.data.prefs.Prefs
import ru.marina.contactlistviewermvp.network.remote.ContactsRemote
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val contactsRemote: ContactsRemote,
    private val contactsDao: ContactsDao,
    private val contactsMapper: ContactsMapper,
    private val prefs: Prefs
) : ContactsRepository {

    override fun getContactsWithCache(): Single<List<Contact>> {
        return if (System.currentTimeMillis() - prefs.lastCacheTime >= CACHE_UPDATE_TIME) {
            getContacts()
        } else {
            getContactsFromCache()
        }
    }

    override fun getContacts(): Single<List<Contact>> {
        return getContactsFromNetwork()
    }

    override fun getSearchContacts(query: String): Single<List<Contact>> {
        return getContactsFromCache()
            .map { contacts ->
                contacts.filter {
                    val queryLowerCase = query.toLowerCase()
                    val phone: String = it.phone.replace(Regex("\\D"), "")
                    val name: String = it.name

                    name.toLowerCase().contains(queryLowerCase) ||
                            phone.contains(queryLowerCase) ||
                            it.phone.contains(queryLowerCase)
                }
            }
            .subscribeOn(Schedulers.io())
    }

    override fun getContactById(id: String): Single<Contact> {
        return contactsDao.getContactById(id)
            .map { contactsMapper.mapFromDbToModel(it) }
            .subscribeOn(Schedulers.io())
    }

    private fun getContactsFromNetwork(): Single<List<Contact>> {
        return Single.zip(
            contactsRemote.getContacts("generated-01.json"),
            contactsRemote.getContacts("generated-02.json"),
            contactsRemote.getContacts("generated-03.json"),
            Function3<List<Contact>, List<Contact>, List<Contact>, List<Contact>>
            { contacts1, contacts2, contacts3 ->
                val allContacts: MutableList<Contact> = mutableListOf()

                allContacts.addAll(contacts1)
                allContacts.addAll(contacts2)
                allContacts.addAll(contacts3)

                contactsDao.updateContacts(contactsMapper.mapFromModelToDbList(allContacts))

                prefs.lastCacheTime = System.currentTimeMillis()

                return@Function3 allContacts
            }
        )
            .map { contacts -> contacts.sortedBy { it.name } }
            .subscribeOn(Schedulers.io())
    }

    private fun getContactsFromCache(): Single<List<Contact>> {
        return contactsDao.getContacts()
            .map { contactsMapper.mapFromDbToModelList(it) }
            .map { contacts -> contacts.sortedBy { it.name } }
            .subscribeOn(Schedulers.io())
    }
}