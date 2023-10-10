package com.titan.apps.todolist.view_model


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titan.apps.todolist.db.Note
import com.titan.apps.todolist.db.NoteDataBase
import com.titan.apps.todolist.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }

    fun getAllTodoList(): LiveData<List<Note>> {
        return repository.todoListData
    }
}