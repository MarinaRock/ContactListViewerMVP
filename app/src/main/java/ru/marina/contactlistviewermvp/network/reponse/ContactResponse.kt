package ru.marina.contactlistviewermvp.network.reponse

data class ContactResponse(
    val id: String,
    val name: String,
    val avatar: String,
    val phone: String,
    val height: Float,
    val biography: String,
    val temperament: TemperamentResponse,
    val educationPeriod: EducationPeriodResponse
)

data class EducationPeriodResponse(
    val start: String,
    val end: String
)

enum class TemperamentResponse {
    melancholic,
    phlegmatic,
    sanguine,
    choleric;
}