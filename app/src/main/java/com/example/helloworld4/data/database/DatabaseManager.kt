package com.example.helloworld4.data.database

import android.content.Context
import androidx.room.Room
import com.example.helloworld4.data.database.dao.NoteDao
import com.example.helloworld4.data.database.dao.UserDao
import com.example.helloworld4.data.database.migration.MIGRATION_1_2

object DatabaseManager {
    private var database: AppDatabase? = null

    fun initDatabase(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "app-database"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    fun getUserDao(): UserDao? {
        return database?.userDao()
    }

    fun getNoteDao(): NoteDao? {
        return database?.noteDao()
    }
}
