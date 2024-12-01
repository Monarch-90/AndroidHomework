package com.example.helloworld4.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.helloworld4.intent.NoteIntent
import com.example.helloworld4.reducer.NoteReducer
import com.example.helloworld4.state.NoteState

class NoteViewModel(private val reducer: NoteReducer) : ViewModel() {
    private val _state = MutableLiveData(NoteState())
    val state: LiveData<NoteState> get() = _state

    fun processIntent(intent: NoteIntent) {
        val currentState = _state.value ?: NoteState()
        val newState = reducer.reduce(currentState, intent)
        _state.value = newState
    }
}