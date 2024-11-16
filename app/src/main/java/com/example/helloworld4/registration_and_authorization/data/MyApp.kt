package com.example.helloworld4.registration_and_authorization.data

import android.app.Application

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseManager.init(this)
    }
}