package com.example.helloworld4.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.helloworld4.data.model.Note

@Dao
interface NoteDao {
    @Insert
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM notes WHERE userId = :userId")
    fun getNotesByUserId(userId: Long): LiveData<List<Note>>
}