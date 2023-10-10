package com.titan.apps.todolist.repository

import com.titan.apps.todolist.db.Note
import com.titan.apps.todolist.db.NoteDao

class MainRepository(private val dao:NoteDao) {

    val todoListData = dao.getAllNotesData()

    suspend fun delete(note:  Note): Int {
        return dao.deleteNote(note)
    }

    suspend fun update(note: Note): Int {
        return dao.updateNote(note)
    }


}

