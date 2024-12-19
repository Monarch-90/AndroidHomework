package com.example.helloworld4.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.helloworld4.data.model.Note
import com.example.helloworld4.data.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: NoteRepository, application: Application) :
    AndroidViewModel(application) {

    fun getUserNotes(userId: Long): LiveData<List<Note>> {
        return noteRepository.getNotesByUserId(userId)
    }

    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note)
        }
    }
}