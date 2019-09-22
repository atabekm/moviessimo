package com.example.feature.list.domain

import com.example.core.network.model.NetworkResponse
import com.example.feature.list.data.MovieListRepository
import com.example.feature.list.domain.converter.MovieConverter
import com.example.feature.list.domain.model.Movie

class DiscoverMoviesUseCase(private val repository: MovieListRepository) {

    suspend operator fun invoke(): NetworkResponse<List<Movie>> {
        val movieResponse = repository.getDiscoverMovies()
        return NetworkResponse(
            isSuccess = movieResponse.isSuccessful,
            data = movieResponse.body()?.results?.map(MovieConverter::fromDataToDomain) ?: listOf(),
            error = movieResponse.errorBody().toString()
        )
    }
}
