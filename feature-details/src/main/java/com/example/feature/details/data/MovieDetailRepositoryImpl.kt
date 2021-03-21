package com.example.feature.details.data

import com.example.feature.details.data.network.DetailService
import com.example.feature.details.domain.repository.MovieDetailRepository

internal class MovieDetailRepositoryImpl(private val detailService: DetailService) :
    MovieDetailRepository {
    override suspend fun getMovieDetails(movieId: Int) = detailService.getMovieDetails(movieId)
}
