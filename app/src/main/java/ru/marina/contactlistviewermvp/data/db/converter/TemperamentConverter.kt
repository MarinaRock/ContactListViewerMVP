package ru.marina.contactlistviewermvp.data.db.converter

import androidx.room.TypeConverter
import ru.marina.contactlistviewermvp.data.db.entity.TemperamentDb

class TemperamentConverter {

    @TypeConverter
    fun toTemperament(value: String) = enumValueOf<TemperamentDb>(value)

    @TypeConverter
    fun fromTemperament(value: TemperamentDb) = value.name
}