package com.example.helloworld4.RegAuth

import android.app.Application

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseManager.init(this)
    }
}