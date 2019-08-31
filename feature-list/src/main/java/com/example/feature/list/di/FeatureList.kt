package com.example.feature.list.di

import com.example.feature.list.data.MovieListRepository
import com.example.feature.list.data.network.ListService
import org.koin.dsl.module
import retrofit2.Retrofit

object FeatureList {

    val module = module {
        single<ListService> { get<Retrofit>().create(ListService::class.java) }
        single { MovieListRepository(get()) }
    }

}