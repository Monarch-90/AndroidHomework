package com.example.helloworld4.notes.state

import com.example.helloworld4.notes.data.Note

data class NoteState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
