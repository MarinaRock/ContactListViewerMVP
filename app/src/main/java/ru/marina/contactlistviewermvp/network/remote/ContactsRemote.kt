package ru.marina.contactlistviewermvp.network.remote

import io.reactivex.Single
import ru.marina.contactlistviewermvp.data.model.Contact

interface ContactsRemote {

    fun getContacts(source: String): Single<List<Contact>>
}