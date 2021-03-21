package com.example.feature.details.domain.repository

import com.example.feature.details.data.model.Movie
import retrofit2.Response

internal interface MovieDetailRepository {
    suspend fun getMovieDetails(movieId: Int): Response<Movie>
}
