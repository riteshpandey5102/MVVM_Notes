package com.example.mvvm_notes.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_notes.data.NotesDao
import com.example.mvvm_notes.domain.model.Note
import kotlinx.coroutines.launch

class NotesViewModel(val dao: NotesDao): ViewModel() {
    var newNoteTitle = ""
    val notes = dao.getAll()
    private val _navigateToNote = MutableLiveData<Long?>()
    val navigateToNote: MutableLiveData<Long?> get() = _navigateToNote

    fun addNote() {
        viewModelScope.launch {
            val note = Note()
            note.title = newNoteTitle
            dao.insert(note)
        }
    }

    fun onNoteItemClicked(noteId: Long) {
        _navigateToNote.value = noteId
    }

    fun onNoteItemNavigated() {
        _navigateToNote.value = null
    }
}
