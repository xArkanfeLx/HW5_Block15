package com.example.room.Interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.room.Contact

@Dao
interface ContactDao {
    @Insert
    suspend fun insert(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * FROM contact_table ORDER BY id ASC")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("DELETE FROM contact_table")
    fun deleteAll()
}