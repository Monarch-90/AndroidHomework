package com.example.helloworld4.view.users

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.helloworld4.R
import com.example.helloworld4.data.database.DatabaseManager
import com.example.helloworld4.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Registration : AppCompatActivity() {
    private val validation = "[a-zA-Z0-9]".toRegex()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_registration)

        val userLogin: EditText = findViewById(R.id.user_login)
        val userPass: EditText = findViewById(R.id.user_pass)
        val userPassConfirm: EditText = findViewById(R.id.user_pass_confirm)
        val buttonReg: Button = findViewById(R.id.button_reg)

        buttonReg.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()
            val passConfirm = userPassConfirm.text.toString().trim()

            if (login == "" || pass == "" || passConfirm == "") {
                Toast.makeText(this, "Not Filled!", Toast.LENGTH_LONG).show()
            } else if (pass != passConfirm) {
                Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_LONG).show()
            } else if (!pass.matches(validation)) {
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_LONG).show()
            } else {
                lifecycleScope.launch {
                    try {
                        withContext(Dispatchers.IO) {
                            val user = User(login = login, pass = pass)
                            DatabaseManager.getUserDao().insertUser(user)
                        }

                        Toast.makeText(
                            this@Registration,
                            "User \"$login\" created",
                            Toast.LENGTH_LONG
                        ).show()

                        userLogin.text.clear()
                        userPass.text.clear()
                        userPassConfirm.text.clear()

                        val intentReg =
                            Intent(this@Registration.baseContext, Authorization::class.java)
                        startActivity(intentReg)
                    } catch (e: Exception) {
                        Toast.makeText(this@Registration, "Error: ${e.message}", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }

        val buttonGoAuth: TextView = findViewById(R.id.button_go_auth)

        val intentAuth = Intent(this.baseContext, Authorization::class.java)
        buttonGoAuth.setOnClickListener {
            startActivity(intentAuth)
        }
    }
}