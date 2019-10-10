package com.example.feature.details.di

import com.example.feature.details.data.MovieDetailRepository
import com.example.feature.details.data.MovieDetailRepositoryImpl
import com.example.feature.details.data.network.DetailService
import com.example.feature.details.domain.GetMovieByIdUseCase
import com.example.feature.details.presentation.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

object FeatureDetail {

    val module = module {
        single { get<Retrofit>().create(DetailService::class.java) }
        single<MovieDetailRepository> { MovieDetailRepositoryImpl(get()) }
        single { GetMovieByIdUseCase(get()) }

        viewModel { DetailsViewModel(get()) }
    }
}
