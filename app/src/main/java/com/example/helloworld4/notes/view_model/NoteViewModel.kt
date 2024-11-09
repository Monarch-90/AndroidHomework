package com.example.helloworld4.notes.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.helloworld4.notes.data.Note

class NoteViewModel : ViewModel() {
    private val _notes = MutableLiveData<List<Note>>(emptyList())
    val notes: LiveData<List<Note>> get() = _notes

    fun addNote(note: Note) {
        _notes.value = _notes.value?.plus(note)
    }
}