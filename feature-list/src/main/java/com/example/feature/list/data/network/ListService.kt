package com.example.feature.list.data.network

import com.example.feature.list.data.model.DiscoverMovie
import io.reactivex.Observable
import retrofit2.http.GET

interface ListService {

    @GET("discover/movie")
    fun getMovieList(): Observable<DiscoverMovie>
}
