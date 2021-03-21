package com.example.feature.list.data.network

import com.example.feature.list.data.model.DiscoverMovie
import retrofit2.Response
import retrofit2.http.GET

internal interface ListService {

    @GET("discover/movie")
    suspend fun getMovieList(): Response<DiscoverMovie>
}
