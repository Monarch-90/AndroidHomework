package com.example.helloworld4.notes.intent

import com.example.helloworld4.notes.data.Note

sealed class NoteIntent {

    data class AddNote(val note: Note) : NoteIntent()
    data class ShareNote(val note: Note) : NoteIntent()
    object LoadNotes : NoteIntent()
}