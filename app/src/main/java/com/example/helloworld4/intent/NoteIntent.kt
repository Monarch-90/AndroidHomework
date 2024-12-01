package com.example.helloworld4.intent

import com.example.helloworld4.data.model.Note

sealed class NoteIntent {

    data class AddNote(val note: Note) : NoteIntent()
    data class ShareNote(val note: Note) : NoteIntent()
    object LoadNotes : NoteIntent()
}