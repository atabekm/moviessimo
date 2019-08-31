package com.example.core.prefs.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object CorePrefs {
    val module = module {
        single<SharedPreferences> {
            androidContext().getSharedPreferences("moviessimo", Context.MODE_PRIVATE)
        }
    }
}
