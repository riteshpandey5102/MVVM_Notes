package com.example.mvvm_notes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_notes.data.NotesDao

class EditNoteViewModelFactory(private val noteId: Long, private val dao: NotesDao)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Long::class.java, NotesDao::class.java).newInstance(noteId, dao)
    }
}
