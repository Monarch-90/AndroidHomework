package com.example.helloworld4.di

import com.example.helloworld4.reducer.NoteReducer
import com.example.helloworld4.view_model.NoteViewModel
import org.koin.dsl.module


val appModule = module {
    single { NoteReducer() }
    single { NoteViewModel(get()) }
}
