package com.titan.apps.todolist.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titan.apps.todolist.repository.AddNoteRepository
import com.titan.apps.todolist.db.Note
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: AddNoteRepository) : ViewModel() {

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }



}