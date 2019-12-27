package com.example.feature.details.data.network

import com.example.feature.details.data.model.Movie
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailService {

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") append: String = "credits"
    ): Observable<Movie>
}
