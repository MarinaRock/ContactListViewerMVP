package ru.marina.contactlistviewermvp.network.remote

import io.reactivex.Single
import ru.marina.contactlistviewermvp.data.mapper.ContactsMapper
import ru.marina.contactlistviewermvp.data.model.Contact
import ru.marina.contactlistviewermvp.network.api.ApiService
import javax.inject.Inject

class ContactsRemoteImpl @Inject constructor(
    private val service: ApiService,
    private val contactsMapper: ContactsMapper
) : ContactsRemote {

    override fun getContacts(source: String): Single<List<Contact>> =
        service.getContacts(source)
            .map { contactsMapper.mapFromResponseToModelList(it) }
}