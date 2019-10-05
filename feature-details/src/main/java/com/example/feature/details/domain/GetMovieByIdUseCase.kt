package com.example.feature.details.domain

import com.example.core.network.model.NetworkResponse
import com.example.feature.details.data.MovieDetailRepository
import com.example.feature.details.domain.converter.MovieConverter
import com.example.feature.details.domain.model.Movie

class GetMovieByIdUseCase(private val repository: MovieDetailRepository) {

    suspend operator fun invoke(movieId: Int): NetworkResponse<Movie> {
        val movieResponse = repository.getMovieDetails(movieId)
        return NetworkResponse(
            isSuccess = movieResponse.isSuccessful,
            data = MovieConverter.fromDataToDomain(movieResponse.body()),
            error = movieResponse.message()
        )
    }
}
