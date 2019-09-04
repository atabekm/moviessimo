package com.example.core.network.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoreNetwork {
    private const val LOGGING_INTERCEPTOR = "logging_interceptor"
    private const val QUERY_INTERCEPTOR = "query_interceptor"
    private const val API_KEY = "api_key"
    private const val IS_DEBUG = "is_debug"
    private const val BASE_URL = "base_url"

    val module = module {
        factory<Converter.Factory> { GsonConverterFactory.create() }
        factory<Interceptor>(named(LOGGING_INTERCEPTOR)) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
        factory(named(QUERY_INTERCEPTOR)) {
            Interceptor { chain ->
                val newUrl = chain.request().url()
                    .newBuilder()
                    .addQueryParameter(API_KEY, getProperty<String>(API_KEY))
                    .build()
                val newRequest = chain.request()
                    .newBuilder()
                    .url(newUrl)
                    .build()
                chain.proceed(newRequest)
            }
        }
        factory {
            OkHttpClient.Builder().apply {
                if (getProperty(IS_DEBUG)) {
                    addInterceptor(get(named(LOGGING_INTERCEPTOR)))
                }
                addInterceptor(get(named(QUERY_INTERCEPTOR)))
            }.build()
        }
        single {
            Retrofit.Builder()
                .baseUrl(getProperty<String>(BASE_URL))
                .client(get())
                .addConverterFactory(get())
                .build()
        }
    }
}