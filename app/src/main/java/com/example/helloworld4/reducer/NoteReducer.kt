package com.example.helloworld4.reducer

import com.example.helloworld4.intent.NoteIntent
import com.example.helloworld4.state.NoteState

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