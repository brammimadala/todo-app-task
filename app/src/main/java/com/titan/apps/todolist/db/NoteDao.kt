package com.titan.apps.todolist.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Update
    suspend fun updateNote(note: Note):Int

    @Delete
    suspend fun deleteNote(note: Note):Int

    @Query("SELECT * FROM note_data_table ORDER BY note_status")
    fun getAllNotesData(): LiveData<List<Note>>
}