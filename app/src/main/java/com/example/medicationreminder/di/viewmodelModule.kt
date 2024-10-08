package com.example.medicationreminder.di

import com.example.medicationreminder.ui.screen.login.LoginViewModel
import com.example.medicationreminder.ui.screen.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
}

