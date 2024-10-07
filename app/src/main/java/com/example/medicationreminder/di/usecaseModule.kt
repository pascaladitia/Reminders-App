package com.example.medicationreminder.di

import com.example.medicationreminder.domain.usecase.LocalUC
import org.koin.dsl.module

val usecaseModule = module {
    factory { LocalUC(get()) }
}
