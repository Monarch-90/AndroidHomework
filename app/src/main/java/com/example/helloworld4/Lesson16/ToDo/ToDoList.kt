package com.example.helloworld4.Lesson16.ToDo

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworld4.Lesson16.Onboarding.FirstActivity
import com.example.helloworld4.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ToDoList : AppCompatActivity() {
    private lateinit var adapter: RvAdapter
    private val toDoList = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolist)

        val recView = findViewById<RecyclerView>(R.id.rec_view)
        val etUserData = findViewById<EditText>(R.id.et_userData)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val tvCallBackLogin = findViewById<TextView>(R.id.call_back_login)

        val callBackLogin = intent.getStringExtra("loginKey")
        tvCallBackLogin.text = callBackLogin

        val buttonHomePage = findViewById<ImageView>(R.id.button_back_home)

        val intentHome = Intent(this.baseContext, FirstActivity::class.java)
        buttonHomePage.setOnClickListener {
            startActivity(intentHome)
        }

        adapter = RvAdapter(toDoList)
        recView.adapter = adapter
        recView.layoutManager = LinearLayoutManager(this)

//        recView.setOnItemClickListener { adapterView, view, i, l ->
//            val oneString = recView.getItemAtPosition(i).toString()
//            adapter.remove(oneString)
//
//            Toast.makeText(this, "\"$oneString\" removed", Toast.LENGTH_LONG).show()
//        }

        val etTitle: EditText = findViewById(R.id.et_title)
        val etText: EditText = findViewById(R.id.et_text)
        val etDate: EditText = findViewById(R.id.et_date)

        buttonAdd.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val text = etText.text.toString().trim()
            val date = getCurrentDate()

            if (title.isNotEmpty() && text.isNotEmpty()) {
                addNote(title, text, date)
                etTitle.text.clear()
                etText.text.clear()

            } else {
                Toast.makeText(this, "Nothing entered!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun addNote(title: String, text: String, date: String) {
        val newNote = Note(title,text, date)
        toDoList.add(newNote)
        adapter.notifyItemInserted(toDoList.size -1)
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
}