package ru.marina.contactlistviewermvp.data.mapper

import ru.marina.contactlistviewermvp.data.db.entity.ContactDb
import ru.marina.contactlistviewermvp.data.model.Contact
import ru.marina.contactlistviewermvp.network.reponse.ContactResponse

interface ContactsMapper {
    fun mapFromResponseToModel(data: ContactResponse): Contact
    fun mapFromResponseToModelList(data: List<ContactResponse>): List<Contact>
    fun mapFromModelToDb(data: Contact): ContactDb
    fun mapFromModelToDbList(data: List<Contact>): List<ContactDb>
    fun mapFromDbToModel(data: ContactDb): Contact
    fun mapFromDbToModelList(data: List<ContactDb>): List<Contact>
}