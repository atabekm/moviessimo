package com.example.core.utils.di

import com.example.core.utils.dispatcher.AppDispatcherProvider
import com.example.core.utils.dispatcher.DispatcherProvider
import org.koin.dsl.module

object CoreUtils {
    val module = module {
        single<DispatcherProvider> { AppDispatcherProvider() }
    }
}
