package com.example.helloworld4.Lesson16.RegAuth

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.TextViewCompat
import com.example.helloworld4.Lesson16.ToDo.ToDoList
import com.example.helloworld4.R

class Authorization : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        val buttonBack: ImageView = findViewById(R.id.button_back)

        val userLogin: EditText = findViewById(R.id.login_auth)
        val userPass: EditText = findViewById(R.id.pass_auth)
        val buttonAuth: Button = findViewById(R.id.button_auth)

        val buttonGoReg: TextView = findViewById(R.id.button_go_reg)

        val intentBack = Intent(this.baseContext, Registration::class.java)
        buttonBack.setOnClickListener {
            startActivity(intentBack)
        }

        buttonAuth.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if (login == "" || pass == "") {
                Toast.makeText(this, "Not Filled!", Toast.LENGTH_LONG).show()
            } else {
                val db = DbHelper(this, null)
                val isAuth = db.getUser(login, pass)

                if (isAuth) {
                    Toast.makeText(this, "Successfully!", Toast.LENGTH_LONG).show()
                    userLogin.text.clear()
                    userPass.text.clear()

                    val intentAuth = Intent(this.baseContext, ToDoList::class.java)
                    startActivity(intentAuth)
                } else
                    Toast.makeText(this, "User $login does not exist.", Toast.LENGTH_LONG).show()
            }
        }

        val intentReg = Intent(this.baseContext, Registration::class.java)
        buttonGoReg.setOnClickListener {
            startActivity(intentReg)
        }
    }
}