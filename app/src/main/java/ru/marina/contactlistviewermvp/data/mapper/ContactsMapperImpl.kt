package ru.marina.contactlistviewermvp.data.mapper

import ru.marina.contactlistviewermvp.data.db.entity.ContactDb
import ru.marina.contactlistviewermvp.data.db.entity.EducationPeriodDb
import ru.marina.contactlistviewermvp.data.db.entity.TemperamentDb
import ru.marina.contactlistviewermvp.data.model.Contact
import ru.marina.contactlistviewermvp.data.model.EducationPeriod
import ru.marina.contactlistviewermvp.data.model.Temperament
import ru.marina.contactlistviewermvp.network.reponse.ContactResponse
import ru.marina.contactlistviewermvp.network.reponse.TemperamentResponse
import java.time.OffsetDateTime
import javax.inject.Inject

class ContactsMapperImpl @Inject constructor() : ContactsMapper {

    override fun mapFromResponseToModel(data: ContactResponse): Contact {
        val temperament = when (data.temperament) {
            TemperamentResponse.melancholic -> Temperament.melancholic
            TemperamentResponse.phlegmatic -> Temperament.phlegmatic
            TemperamentResponse.sanguine -> Temperament.sanguine
            TemperamentResponse.choleric -> Temperament.choleric
        }
        val educationPeriod = EducationPeriod(
            OffsetDateTime.parse(data.educationPeriod.start),
            OffsetDateTime.parse(data.educationPeriod.end)
        )

        return Contact(
            id = data.id,
            name = data.name,
            avatar = data.avatar,
            phone = data.phone,
            height = data.height,
            biography = data.biography,
            temperament = temperament,
            educationPeriod = educationPeriod
        )
    }

    override fun mapFromResponseToModelList(data: List<ContactResponse>): List<Contact> =
        data.map { mapFromResponseToModel(it) }

    override fun mapFromModelToDb(data: Contact): ContactDb {
        val temperament = when (data.temperament) {
            Temperament.melancholic -> TemperamentDb.melancholic
            Temperament.phlegmatic -> TemperamentDb.phlegmatic
            Temperament.sanguine -> TemperamentDb.sanguine
            Temperament.choleric -> TemperamentDb.choleric
        }
        val educationPeriod = EducationPeriodDb(
            data.educationPeriod.start,
            data.educationPeriod.end
        )

        return ContactDb(
            id = data.id,
            name = data.name,
            avatar = data.avatar,
            phone = data.phone,
            height = data.height,
            biography = data.biography,
            temperament = temperament,
            educationPeriod = educationPeriod
        )
    }

    override fun mapFromModelToDbList(data: List<Contact>): List<ContactDb> =
        data.map { mapFromModelToDb(it) }

    override fun mapFromDbToModel(data: ContactDb): Contact {
        val temperament = when (data.temperament) {
            TemperamentDb.melancholic -> Temperament.melancholic
            TemperamentDb.phlegmatic -> Temperament.phlegmatic
            TemperamentDb.sanguine -> Temperament.sanguine
            TemperamentDb.choleric -> Temperament.choleric
        }
        val educationPeriod = EducationPeriod(
            data.educationPeriod.start,
            data.educationPeriod.end
        )

        return Contact(
            id = data.id,
            name = data.name,
            avatar = data.avatar,
            phone = data.phone,
            height = data.height,
            biography = data.biography,
            temperament = temperament,
            educationPeriod = educationPeriod
        )
    }

    override fun mapFromDbToModelList(data: List<ContactDb>): List<Contact> =
        data.map { mapFromDbToModel(it) }
}