package com.example.feature.list.domain.converter

import com.example.feature.list.domain.model.Movie

object MovieConverter {

    fun fromDataToDomain(movie: com.example.feature.list.data.model.Movie): Movie {
        return Movie(
            id = movie.id,
            posterImage = "https://image.tmdb.org/t/p/w185${movie.posterPath}"
        )
    }
}
