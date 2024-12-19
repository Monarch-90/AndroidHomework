package com.example.helloworld4

import android.app.Application
import com.example.helloworld4.data.database.DatabaseManager
import com.example.helloworld4.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        DatabaseManager.initDatabase(this)

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}
