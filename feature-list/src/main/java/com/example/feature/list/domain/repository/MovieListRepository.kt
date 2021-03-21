package com.example.feature.list.domain.repository

import com.example.feature.list.data.model.DiscoverMovie
import retrofit2.Response

internal interface MovieListRepository {
    suspend fun getDiscoverMovies(): Response<DiscoverMovie>
}
