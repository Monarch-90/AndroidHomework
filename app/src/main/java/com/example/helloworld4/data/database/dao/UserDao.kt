package com.example.helloworld4.data.database.dao

import androidx.room.*
import com.example.helloworld4.data.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE login = :login AND pass = :pass")
    suspend fun getUser(login: String, pass: String): User?

    @Query("SELECT login FROM users WHERE login = :currentLogin LIMIT 1")
    suspend fun getCurrentUserLogin(currentLogin: String): String?

    @Query("SELECT * FROM users WHERE login = :login LIMIT 1")
    suspend fun getUserByLogin(login: String): User?
}
