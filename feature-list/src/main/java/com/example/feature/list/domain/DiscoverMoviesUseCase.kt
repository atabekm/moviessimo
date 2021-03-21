package com.example.feature.list.domain

import com.example.core.network.model.NetworkResponse
import com.example.feature.list.domain.converter.toDomain
import com.example.feature.list.domain.model.Movie
import com.example.feature.list.domain.repository.MovieListRepository

internal class DiscoverMoviesUseCase(private val repository: MovieListRepository) {

    suspend operator fun invoke(): NetworkResponse<List<Movie>> {
        val movieResponse = repository.getDiscoverMovies()
        return NetworkResponse(
            isSuccess = movieResponse.isSuccessful,
            data = movieResponse.body()?.results?.map { it.toDomain() } ?: listOf(),
            error = movieResponse.message()
        )
    }
}
