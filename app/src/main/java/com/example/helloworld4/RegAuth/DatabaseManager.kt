package com.example.helloworld4.RegAuth

import android.content.Context
import androidx.room.Room

object DatabaseManager {
    private lateinit var database: AppDatabase

    fun init(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "users-database"
        ).build()
    }

    fun getUserDao(): UserDao {
        return database.userDao()
    }
}