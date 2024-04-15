package com.example.mvvm_notes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_notes.data.NotesDao
import kotlinx.coroutines.launch

class EditNoteViewModel(noteId: Long, private val dao: NotesDao): ViewModel() {
    val note = dao.get(noteId)
    private val _navigateToList = MutableLiveData<Boolean>()
    val navigateToList: LiveData<Boolean> get() = _navigateToList

    fun updateNoteItem() {
        viewModelScope.launch {
            note.value?.let { dao.update(it) }
            _navigateToList.value = true
        }
    }

    fun deleteNoteItem() {
        viewModelScope.launch {
            note.value?.let { dao.delete(it) }
            _navigateToList.value = true
        }
    }

    fun onNavigatedToList() {
        _navigateToList.value = false
    }
}
