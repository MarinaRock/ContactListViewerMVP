package ru.marina.contactlistviewermvp.data.db.dao

import androidx.room.*
import io.reactivex.Single
import ru.marina.contactlistviewermvp.data.db.entity.ContactDb

@Dao
abstract class ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertContacts(contacts: List<ContactDb>)

    @Query("SElECT * FROM ContactDb")
    abstract fun getContacts(): Single<List<ContactDb>>

    @Query("SELECT * FROM ContactDb WHERE id = :id")
    abstract fun getContactById(id: String): Single<ContactDb>

    @Query("DELETE FROM ContactDb")
    abstract fun deleteContacts()

    @Transaction
    open fun updateContacts(contacts: List<ContactDb>) {
        deleteContacts()
        insertContacts(contacts)
    }
}