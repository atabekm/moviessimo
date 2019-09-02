package com.example.feature.list.domain

import com.example.feature.list.data.MovieListRepository
import com.example.feature.list.data.model.DiscoverMovie
import retrofit2.Response

class DiscoverMoviesUseCase(private val repository: MovieListRepository) {

    suspend operator fun invoke(): Response<DiscoverMovie> {
        return repository.getDiscoverMovies()
    }
}