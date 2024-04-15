package com.example.mvvm_notes

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mvvm_notes.data.NotesDao
import com.example.mvvm_notes.data.NotesDatabase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.mvvm_notes.domain.model.Note

@RunWith(AndroidJUnit4::class)
class NotesDAOTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var testDatabase: NotesDatabase
    private lateinit var testDao: NotesDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        testDatabase=Room.inMemoryDatabaseBuilder(
            context,
            NotesDatabase::class.java
        ).allowMainThreadQueries().build()
        testDao=testDatabase.notesDao
    }

    @After
    fun closeDatabase(){
        testDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun database_returnsSizeOfOneAndCorrectTitle_whenNewTodoInserted() = runTest {
        val newNote = Note(
            1L,
            "First note inserted"
        )

        testDao.insert(newNote)
        val notes = testDao.getAll().getOrAwaitValue()
        assertThat(notes.size).isEqualTo(1)
        val insertedNote = testDao.get(1).getOrAwaitValue()
        assertThat(insertedNote.title).isEqualTo(newNote.title)
    }

    @Test
    @Throws(Exception::class)
    fun database_returnsCorrectData_whenNoteUpdated() = runTest {
        val newNote = Note(
            1L,
            "First note inserted"
        )

        testDao.insert(newNote)
        newNote.title = "First note updated"
        testDao.update(newNote)
        val updatedNote = testDao.get(1).getOrAwaitValue()
        assertThat(updatedNote.title).isEqualTo("First note updated")
    }

    @Test
    @Throws(Exception::class)
    fun database_returnsZero_whenAllTodosDeleted() = runTest {
        val newNote = Note(
            1L,
            "First Note Inserted"
        )

        testDao.insert(newNote)
        testDao.delete(newNote)
        val note = testDao.getAll().getOrAwaitValue()
        assertThat(note.size).isEqualTo(0)
    }

}