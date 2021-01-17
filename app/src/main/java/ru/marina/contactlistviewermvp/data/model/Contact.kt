package ru.marina.contactlistviewermvp.data.model

import java.time.OffsetDateTime

data class Contact(
    val id: String,
    val name: String,
    val avatar: String,
    val phone: String,
    val height: Float,
    val biography: String,
    val temperament: Temperament,
    val educationPeriod: EducationPeriod
)

data class EducationPeriod(
    val start: OffsetDateTime,
    val end: OffsetDateTime
)

enum class Temperament {
    melancholic,
    phlegmatic,
    sanguine,
    choleric;
}