package com.example.helloworld4.Lesson16.RegAuth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.helloworld4.Lesson16.ToDo.ToDoList
import com.example.helloworld4.R

class Authorization : AppCompatActivity() {
    private lateinit var userLogin: EditText
    private lateinit var userPass: EditText
    private lateinit var userLoginAndPass: EditText
    private lateinit var buttonAuth: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        userLogin = findViewById(R.id.login_auth)
        userPass = findViewById(R.id.pass_auth)
        buttonAuth = findViewById(R.id.button_auth)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isLoginNotEmpty = userLogin.text.isNotEmpty()
                val isPasswordNotEmpty = userPass.text.isNotEmpty()
                buttonAuth.isEnabled = isLoginNotEmpty && isPasswordNotEmpty
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        userLogin.addTextChangedListener(textWatcher)
        userPass.addTextChangedListener(textWatcher)

        buttonAuth.isEnabled = false

//        userLoginAndPass.doOnTextChanged { _, _, _, _ -> Bool
//            buttonAuth.isEnabled = userLogin.text.isNotEmpty()
//            check()
//        }

        buttonAuth.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()

            val db = DbHelper(this, null)
            val isAuth = db.getUser(login, pass)

            if (isAuth) {
                Toast.makeText(this, "Successfully!", Toast.LENGTH_LONG).show()
                userLogin.text.clear()
                userPass.text.clear()

                val intentAuth = Intent(this.baseContext, ToDoList::class.java)
                intentAuth.putExtra("loginKey", login)
                setResult(RESULT_OK, intentAuth)
                startActivity(intentAuth)
            } else
                Toast.makeText(this, "User \"$login\" does not exist.", Toast.LENGTH_LONG).show()
        }

        val buttonGoReg: TextView = findViewById(R.id.button_go_reg)

        val intentReg = Intent(this.baseContext, Registration::class.java)
        buttonGoReg.setOnClickListener {
            startActivity(intentReg)
        }
    }

//    fun combining(){
//        userLoginAndPass = userLogin
//        userLoginAndPass = userPass
//
//        buttonAuth.isEnabled = userLoginAndPass.text.isNotEmpty()
//    }
}