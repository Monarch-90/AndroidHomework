package com.example.helloworld4.registration_and_authorization.data

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE login = :login AND pass = :pass")
    fun getUser(login: String, pass: String): User?
}
