package com.example.feature.list.data

import com.example.feature.list.data.model.DiscoverMovie
import com.example.feature.list.data.network.ListService
import retrofit2.Response

interface MovieListRepository {
    suspend fun getDiscoverMovies(apiKey: String): Response<DiscoverMovie>
}

class MovieListRepositoryImpl(private val listService: ListService) : MovieListRepository {

    override suspend fun getDiscoverMovies(apiKey: String) = listService.getMovieList(apiKey)
}