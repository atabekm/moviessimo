package com.example.feature.list.data.network

import com.example.feature.list.data.model.DiscoverMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ListService {

    @GET("discover/movie")
    suspend fun getMovieList(@Query("api_key") apiKey: String): Response<DiscoverMovie>

}