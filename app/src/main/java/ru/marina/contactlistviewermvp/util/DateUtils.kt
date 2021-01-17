package ru.marina.contactlistviewermvp.util

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object DateUtils {

    fun getStandardDate(date: OffsetDateTime): String {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return dateTimeFormatter.format(date)
    }
}