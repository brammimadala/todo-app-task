package com.titan.apps.todolist.vm_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.titan.apps.todolist.repository.AddNoteRepository
import com.titan.apps.todolist.view_model.NoteViewModel

class AddNoteViewModelFactory(
    private val repository: AddNoteRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
