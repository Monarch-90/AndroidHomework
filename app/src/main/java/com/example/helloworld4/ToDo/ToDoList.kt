package com.example.helloworld4.ToDo

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworld4.Constants
import com.example.helloworld4.R

class ToDoList : AppCompatActivity() {
    private lateinit var adapter: RvAdapter
    private val toDoList = mutableListOf<Note>()
    private lateinit var noteDetailLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolist)

        val recView = findViewById<RecyclerView>(R.id.rec_view)
        val btnAdd = findViewById<AppCompatImageView>(R.id.iv_btn_add)

        adapter = RvAdapter(toDoList)
        recView.adapter = adapter
        recView.layoutManager = LinearLayoutManager(this)

        noteDetailLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data = result.data
                    val title = data?.getStringExtra(Constants.TITLE_KEY) ?: ""
                    val text = data?.getStringExtra(Constants.TEXT_KEY) ?: ""
                    val date = data?.getStringExtra(Constants.DATA_KEY) ?: ""
                    val imageUri = data?.getStringExtra(Constants.IMAGE_KEY)

                    val newNote = Note(title, text, date, imageUri)
                    toDoList.add(newNote)
                    adapter.updateNotes(toDoList)
                }
            }

        btnAdd.setOnClickListener {
            val intent = Intent(this, NoteDetailActivity::class.java)
            noteDetailLauncher.launch(intent)
        }
    }
}