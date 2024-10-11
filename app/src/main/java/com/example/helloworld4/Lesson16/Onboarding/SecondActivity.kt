package com.example.helloworld4.Lesson16.Onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.helloworld4.R

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val buttonRight = findViewById<ImageView>(R.id.button_right_two)
        val buttonLeft = findViewById<ImageView>(R.id.button_left_two)

        val intentRightButton = Intent(this.baseContext, ThirdActivity::class.java)
        val intentLeftButton = Intent(this.baseContext, FirstActivity::class.java)

        buttonRight.setOnClickListener {
            startActivity(intentRightButton)
        }

        buttonLeft.setOnClickListener {
            startActivity(intentLeftButton)
        }
    }
}