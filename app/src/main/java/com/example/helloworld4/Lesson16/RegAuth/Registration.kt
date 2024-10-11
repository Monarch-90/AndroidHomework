package com.example.helloworld4.Lesson16.RegAuth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.helloworld4.Lesson16.Onboarding.ThirdActivity
import com.example.helloworld4.R

class Registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val buttonBack: ImageView = findViewById(R.id.button_back)

        val userLogin: EditText = findViewById(R.id.user_login)
        val userEmail: EditText = findViewById(R.id.user_email)
        val userPass: EditText = findViewById(R.id.user_pass)
        val userPassConfirm: EditText = findViewById(R.id.user_pass_confirm)
        val buttonReg: Button = findViewById(R.id.button_reg)

        val buttonGoAuth: TextView = findViewById(R.id.button_go_auth)

        val intentBack = Intent(this.baseContext, ThirdActivity::class.java)
        buttonBack.setOnClickListener {
            startActivity(intentBack)
        }

        buttonReg.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val pass = userPass.text.toString().trim()
            val passConfirm = userPassConfirm.text.toString().trim()

            if (login == "" || email == "" || pass == "" || passConfirm == "")
                Toast.makeText(this, "Not Filled!", Toast.LENGTH_LONG).show()
            else if (pass != passConfirm)
                Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_LONG).show()
            else {
                val user = User(login, email, pass)

                val db = DbHelper(this, null)
                db.addUser(user)
                Toast.makeText(this, "User $login created", Toast.LENGTH_LONG).show()

                userLogin.text.clear()
                userEmail.text.clear()
                userPass.text.clear()
                userPassConfirm.text.clear()

                val intent = Intent(this.baseContext, Authorization::class.java)
                startActivity(intent)
            }
        }

        val intentAuth = Intent(this.baseContext, Authorization::class.java)
        buttonGoAuth.setOnClickListener {
            startActivity(intentAuth)
        }
    }
}