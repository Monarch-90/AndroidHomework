package com.example.helloworld4.data.database

import androidx.room.*
import com.example.helloworld4.data.model.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE login = :login AND pass = :pass")
    fun getUser(login: String, pass: String): User?
}
