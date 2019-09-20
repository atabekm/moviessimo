package com.example.moviessimo.di

import com.example.feature.details.navigation.MovieDetailsNavigation
import com.example.feature.list.navigation.MovieListNavigation
import com.example.moviessimo.navigation.Navigator
import org.koin.dsl.binds
import org.koin.dsl.module

object Navigation {

    val module = module {
        single { Navigator() } binds arrayOf(
            MovieListNavigation::class,
            MovieDetailsNavigation::class
        )
    }
}
