package com.example.helloworld4.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.helloworld4.data.database.dao.NoteDao
import com.example.helloworld4.data.database.dao.UserDao
import com.example.helloworld4.data.model.Note
import com.example.helloworld4.data.model.User

@Database(entities = [User::class, Note::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun noteDao(): NoteDao
}
