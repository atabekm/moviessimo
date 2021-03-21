package com.example.feature.details.domain

import com.example.core.network.model.NetworkResponse
import com.example.feature.details.domain.converter.toDomain
import com.example.feature.details.domain.model.Movie
import com.example.feature.details.domain.repository.MovieDetailRepository

internal class GetMovieByIdUseCase(private val repository: MovieDetailRepository) {

    suspend operator fun invoke(movieId: Int): NetworkResponse<Movie> {
        val movieResponse = repository.getMovieDetails(movieId)
        return NetworkResponse(
            isSuccess = movieResponse.isSuccessful,
            data = movieResponse.body().toDomain(),
            error = movieResponse.message()
        )
    }
}
