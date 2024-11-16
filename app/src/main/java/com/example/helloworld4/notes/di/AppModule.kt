package com.example.helloworld4.notes.di

import com.example.helloworld4.notes.reducer.NoteReducer
import com.example.helloworld4.notes.view_model.NoteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single { NoteReducer() }
    single { NoteViewModel(get()) }
}
