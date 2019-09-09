package com.example.feature.details.domain

import com.example.feature.details.data.MovieDetailRepository
import com.example.feature.details.data.model.Movie
import retrofit2.Response

class GetMovieByIdUseCase(private val repository: MovieDetailRepository) {

    suspend operator fun invoke(movieId: Int): Response<Movie> {
        return repository.getMovieDetails(movieId)
    }
}
