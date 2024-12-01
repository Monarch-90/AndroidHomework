package com.example.helloworld4.state

import com.example.helloworld4.data.model.Note

data class NoteState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
