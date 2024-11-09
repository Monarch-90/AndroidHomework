package com.example.helloworld4

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.helloworld4.onboarding.FirstActivity
import com.example.helloworld4.registration_and_authorization.data.DatabaseManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val userDao = DatabaseManager.getUserDao()

        CoroutineScope(Dispatchers.IO).launch {
            userDao.getUser("login", "pass")
            withContext(Dispatchers.Main) {
            }
        }

        val handler = android.os.Handler(Looper.getMainLooper())

        handler.postDelayed({
            val intentFirstActivity = Intent(this, FirstActivity::class.java)
            startActivity(intentFirstActivity)
            finish()
        }, 1000)


    }
}