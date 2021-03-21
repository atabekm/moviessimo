package com.example.feature.list.di

import com.example.feature.list.data.network.ListService
import com.example.feature.list.data.repository.MovieListRepositoryImpl
import com.example.feature.list.domain.DiscoverMoviesUseCase
import com.example.feature.list.domain.repository.MovieListRepository
import com.example.feature.list.presentation.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

object FeatureList {

    val module = module {
        single { get<Retrofit>().create(ListService::class.java) }
        single<MovieListRepository> { MovieListRepositoryImpl(get()) }
        single { DiscoverMoviesUseCase(get()) }

        viewModel { ListViewModel(get()) }
    }
}
