package com.example.feature.details.data.network

import com.example.feature.details.data.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface DetailService {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") append: String = "credits"
    ): Response<Movie>
}
