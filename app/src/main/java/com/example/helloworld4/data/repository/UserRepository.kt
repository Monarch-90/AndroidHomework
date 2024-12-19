package com.example.helloworld4.data.repository

import com.example.helloworld4.data.database.dao.UserDao
import com.example.helloworld4.data.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getUser(login: String, pass: String): User? {
        return userDao.getUser(login, pass)
    }

    suspend fun currentUserLogin(currentLogin: String): String? {
        return try {
            val login = userDao.getCurrentUserLogin(currentLogin)
            login
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getUserByLogin(login: String): User? {
        return userDao.getUserByLogin(login)
    }
}