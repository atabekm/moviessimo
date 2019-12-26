package com.example.feature.list.data

import com.example.feature.list.data.model.DiscoverMovie
import com.example.feature.list.data.network.ListService
import io.reactivex.Observable

interface MovieListRepository {
    fun getDiscoverMovies(): Observable<DiscoverMovie>
}

class MovieListRepositoryImpl(private val listService: ListService) : MovieListRepository {

    override fun getDiscoverMovies(): Observable<DiscoverMovie> {
        return listService.getMovieList()
    }
}
