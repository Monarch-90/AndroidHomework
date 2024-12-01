package com.example.helloworld4.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.helloworld4.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
