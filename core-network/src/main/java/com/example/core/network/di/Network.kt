package com.example.core.network.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {

    val module = module {
        factory<Converter.Factory> { GsonConverterFactory.create() }
        factory {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
        factory<OkHttpClient> {
            OkHttpClient.Builder()
                .addInterceptor(get())
                .build()
        }
        single<Retrofit> {
            Retrofit.Builder()
                .baseUrl(getProperty<String>("baseUrl"))
                .client(get())
                .addConverterFactory(get())
                .build()
        }
    }
}