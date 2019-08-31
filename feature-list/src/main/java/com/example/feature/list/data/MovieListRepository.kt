package com.example.feature.list.data

import com.example.feature.list.data.network.ListService

class MovieListRepository(private val listService: ListService) {

    suspend fun getDiscoverMovies(apiKey: String) = listService.getMovieList(apiKey)
}