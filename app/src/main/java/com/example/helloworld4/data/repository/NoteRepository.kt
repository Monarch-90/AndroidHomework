package com.example.helloworld4.data.repository

import androidx.lifecycle.LiveData
import com.example.helloworld4.data.database.dao.NoteDao
import com.example.helloworld4.data.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    fun getNotesByUserId(userId: Long): LiveData<List<Note>> {
        return noteDao.getNotesByUserId(userId)
    }
}