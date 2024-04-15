package com.example.mvvm_notes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.mvvm_notes.data.NotesDao
import com.example.mvvm_notes.domain.model.Note
import com.example.mvvm_notes.presentation.viewmodel.NotesViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var testDao: NotesDao
    private lateinit var notesViewModel: NotesViewModel
    private val emptyLiveDataList = MutableLiveData<List<Note>>()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        every { testDao.getAll() } returns emptyLiveDataList
        notesViewModel = NotesViewModel(testDao)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addNote calls insert method in DAO once`() = runTest {
        coEvery { testDao.insert(any()) } just Runs
        notesViewModel.newNoteTitle = "New test note"
        launch { notesViewModel.addNote() }
        advanceUntilIdle()
        coVerify(exactly = 1) { testDao.insert(any()) }
    }

    @Test
    fun `onNoteItemClicked sets noteId LiveData`() {
        // Given
        val noteId = 1L

        // When
        notesViewModel.onNoteItemClicked(noteId)

        // Then
        assertThat(notesViewModel.navigateToNote.value).isEqualTo(noteId)
    }

    @Test
    fun `onNoteItemNavigated resets the navigateToNote value to null`() {
        // Given
        notesViewModel.onNoteItemClicked(1L)

        // When
        notesViewModel.onNoteItemNavigated()

        // Then
        assertThat(notesViewModel.navigateToNote.value).isNull()
    }

}
