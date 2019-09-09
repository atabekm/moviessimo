package com.example.moviessimo.di

import com.example.feature.list.navigation.MovieDetailsNavigation
import com.example.moviessimo.navigation.Navigator
import org.koin.dsl.binds
import org.koin.dsl.module

object Navigation {

    val module = module {
        single { Navigator() } binds arrayOf(MovieDetailsNavigation::class)
    }
}
