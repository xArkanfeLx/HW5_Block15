package com.example.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class Contact(
    @ColumnInfo(name = "family") var family: String,
    @ColumnInfo(name = "phone") var phone: String,
    @ColumnInfo(name = "date") var date: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}