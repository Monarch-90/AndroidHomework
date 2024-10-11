package com.example.helloworld4.Lesson16.ToDo

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.helloworld4.Lesson16.Onboarding.FirstActivity
import com.example.helloworld4.Lesson16.RegAuth.Authorization
import com.example.helloworld4.R

class ToDoList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolist)

        val buttonBack = findViewById<ImageView>(R.id.button_back)
        val buttonHomePage = findViewById<ImageView>(R.id.button_back_home)

        val listView = findViewById<ListView>(R.id.listView)
        val userData = findViewById<EditText>(R.id.userData)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)

        val intentBack = Intent(this.baseContext, Authorization::class.java)
        buttonBack.setOnClickListener {
            startActivity(intentBack)
        }

        val intentHome = Intent(this.baseContext, FirstActivity::class.java)
        buttonHomePage.setOnClickListener {
            startActivity(intentHome)
        }

        val toDoList: MutableList<String> = mutableListOf()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, toDoList)
        listView.adapter = adapter

        listView.setOnItemClickListener { adapterView, view, i, l ->
            val oneString = listView.getItemAtPosition(i).toString()
            adapter.remove(oneString)

            Toast.makeText(this, "$oneString removed", Toast.LENGTH_LONG).show()
        }

        buttonAdd.setOnClickListener {
            val text = userData.text.toString().trim()
            if (text != "") {
                adapter.insert(text, 0)
                userData.text.clear()
            } else {
                Toast.makeText(this, "Nothing entered!", Toast.LENGTH_LONG).show()
            }
        }
    }
}