package ru.marina.contactlistviewermvp.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity
data class ContactDb(
    @PrimaryKey
    val id: String,
    val name: String,
    val avatar: String,
    val phone: String,
    val height: Float,
    val biography: String,
    val temperament: TemperamentDb,
    @Embedded
    val educationPeriod: EducationPeriodDb
)

data class EducationPeriodDb(
    val start: OffsetDateTime,
    val end: OffsetDateTime
)

enum class TemperamentDb {
    melancholic,
    phlegmatic,
    sanguine,
    choleric;
}