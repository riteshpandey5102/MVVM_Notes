package com.example.mvvm_notes.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mvvm_notes.domain.model.Note

@Dao
interface NotesDao {

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes_table WHERE id = :noteId")
    fun get(noteId: Long): LiveData<Note>

    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    fun getAll(): LiveData<List<Note>>
}
