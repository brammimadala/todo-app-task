package com.titan.apps.todolist.repository

import com.titan.apps.todolist.db.Note
import com.titan.apps.todolist.db.NoteDao

class AddNoteRepository(private val dao: NoteDao) {

    suspend fun insert(note: Note): Long {
        return dao.insertNote(note)
    }
}