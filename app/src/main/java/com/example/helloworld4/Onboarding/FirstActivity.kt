package com.example.helloworld4.Onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.helloworld4.R

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        val button = findViewById<ImageView>(R.id.button_right_one)

        val intent = Intent(this.baseContext, SecondActivity::class.java)

        button.setOnClickListener {
            startActivity(intent)
        }
    }
}