package com.example.feature.list.data.repository

import com.example.feature.list.data.network.ListService
import com.example.feature.list.domain.repository.MovieListRepository

internal class MovieListRepositoryImpl(private val listService: ListService) : MovieListRepository {
    override suspend fun getDiscoverMovies() = listService.getMovieList()
}
