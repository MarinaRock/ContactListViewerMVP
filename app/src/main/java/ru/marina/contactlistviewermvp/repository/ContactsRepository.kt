package ru.marina.contactlistviewermvp.repository

import io.reactivex.Single
import ru.marina.contactlistviewermvp.data.model.Contact

interface ContactsRepository {
    fun getContactsWithCache(): Single<List<Contact>>
    fun getContacts(): Single<List<Contact>>
    fun getSearchContacts(query: String): Single<List<Contact>>
    fun getContact(id: String): Single<Contact>
}