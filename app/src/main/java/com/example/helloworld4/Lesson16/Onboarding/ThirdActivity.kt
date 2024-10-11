package com.example.helloworld4.Lesson16.Onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.helloworld4.Lesson16.RegAuth.Registration
import com.example.helloworld4.R

class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val buttonRight = findViewById<ImageView>(R.id.button_right_three)
        val buttonLeft = findViewById<ImageView>(R.id.button_left_three)

        val intentRightButton = Intent(this.baseContext, Registration::class.java)
        val intentLeftButton = Intent(this.baseContext, SecondActivity::class.java)

        buttonRight.setOnClickListener {
            startActivity(intentRightButton)
        }

        buttonLeft.setOnClickListener {
            startActivity(intentLeftButton)
        }
    }
}