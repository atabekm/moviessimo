package com.example.feature.details.data

import com.example.feature.details.data.model.Movie
import com.example.feature.details.data.network.DetailService
import io.reactivex.Observable

interface MovieDetailRepository {
    fun getMovieDetails(movieId: Int): Observable<Movie>
}

class MovieDetailRepositoryImpl(private val detailService: DetailService) : MovieDetailRepository {

    override fun getMovieDetails(movieId: Int) = detailService.getMovieDetails(movieId)
}
