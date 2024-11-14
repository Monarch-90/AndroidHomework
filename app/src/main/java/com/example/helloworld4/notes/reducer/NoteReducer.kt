package com.example.helloworld4.notes.reducer

import com.example.helloworld4.notes.intent.NoteIntent
import com.example.helloworld4.notes.state.NoteState

class NoteReducer {

    fun reduce(state: NoteState, intent: NoteIntent): NoteState {
        return when (intent) {
            is NoteIntent.AddNote -> state.copy(notes = state.notes + intent.note)
            is NoteIntent.ShareNote -> {
                state.copy(notes = state.notes)
            }

            is NoteIntent.LoadNotes -> state.copy(isLoading = true)
        }
    }
}