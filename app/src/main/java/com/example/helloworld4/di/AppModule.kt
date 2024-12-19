package com.example.helloworld4.di

import com.example.helloworld4.data.database.DatabaseManager
import com.example.helloworld4.data.repository.NoteRepository
import com.example.helloworld4.data.repository.UserRepository
import com.example.helloworld4.view_model.AuthorizationViewModel
import com.example.helloworld4.view_model.ImageViewModel
import com.example.helloworld4.view_model.NoteViewModel
import com.example.helloworld4.view_model.OnboardingViewModel
import com.example.helloworld4.view_model.RegistrationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    viewModel { OnboardingViewModel(get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { AuthorizationViewModel(get(), get()) }
    viewModel { NoteViewModel(get(), get()) }
    viewModel { ImageViewModel() }

    single { UserRepository(DatabaseManager.getUserDao()!!) }
    single { NoteRepository(DatabaseManager.getNoteDao()!!) }
}