package com.example.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @ColumnInfo(name = "family") var family: String,
    @ColumnInfo(name = "phone") var phone: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}