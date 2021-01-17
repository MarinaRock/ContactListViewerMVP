package ru.marina.contactlistviewermvp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.marina.contactlistviewermvp.data.db.converter.DateConverter
import ru.marina.contactlistviewermvp.data.db.converter.TemperamentConverter
import ru.marina.contactlistviewermvp.data.db.dao.ContactsDao
import ru.marina.contactlistviewermvp.data.db.entity.ContactDb

@Database(
    entities = [
        ContactDb::class
    ],
    version = 1
)
@TypeConverters(
    TemperamentConverter::class,
    DateConverter::class
)
abstract class AppDb : RoomDatabase() {

    abstract fun contactsDao(): ContactsDao
}