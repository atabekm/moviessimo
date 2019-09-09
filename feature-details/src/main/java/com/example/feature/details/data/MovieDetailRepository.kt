package com.example.feature.details.data

import com.example.feature.details.data.model.Movie
import com.example.feature.details.data.network.DetailService
import retrofit2.Response

interface MovieDetailRepository {
    suspend fun getMovieDetails(movieId: Int): Response<Movie>
}

class MovieDetailRepositoryImpl(private val detailService: DetailService) : MovieDetailRepository {

    override suspend fun getMovieDetails(movieId: Int) = detailService.getMovieDetails(movieId)
}
