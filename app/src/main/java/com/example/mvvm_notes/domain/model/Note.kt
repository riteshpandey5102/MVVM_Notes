package com.example.mvvm_notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var title: String = "",)
