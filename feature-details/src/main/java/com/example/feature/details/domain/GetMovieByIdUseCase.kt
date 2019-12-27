package com.example.feature.details.domain

import com.example.feature.details.data.MovieDetailRepository
import com.example.feature.details.domain.converter.MovieConverter
import com.example.feature.details.domain.model.Movie
import io.reactivex.Observable

class GetMovieByIdUseCase(private val repository: MovieDetailRepository) {

    operator fun invoke(movieId: Int): Observable<Movie> {
        return repository.getMovieDetails(movieId)
            .map(MovieConverter::fromDataToDomain)
    }
}
