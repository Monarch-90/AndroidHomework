package com.example.helloworld4.notes.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.helloworld4.notes.intent.NoteIntent
import com.example.helloworld4.notes.reducer.NoteReducer
import com.example.helloworld4.notes.state.NoteState

class NoteViewModel(private val reducer: NoteReducer) : ViewModel() {
    private val _state = MutableLiveData(NoteState())
    val state: LiveData<NoteState> get() = _state

    fun processIntent(intent: NoteIntent) {
        val currentState = _state.value ?: NoteState()
        val newState = reducer.reduce(currentState, intent)
        _state.value = newState
    }
}