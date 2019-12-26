package com.example.feature.list.domain

import com.example.feature.list.data.MovieListRepository
import com.example.feature.list.domain.converter.MovieConverter
import com.example.feature.list.domain.model.Movie
import io.reactivex.Observable

class DiscoverMoviesUseCase(private val repository: MovieListRepository) {

    operator fun invoke(): Observable<List<Movie>> {
        return repository.getDiscoverMovies()
            .flatMap {
                Observable.fromIterable(it.results)
            }
            .map(MovieConverter::fromDataToDomain)
            .toList()
            .toObservable()
    }
}
