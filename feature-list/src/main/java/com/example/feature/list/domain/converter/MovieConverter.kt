package com.example.feature.list.domain.converter

import com.example.feature.list.domain.model.Movie

internal fun com.example.feature.list.data.model.Movie.toDomain(): Movie {
    return Movie(
        id = this.id,
        posterImage = "https://image.tmdb.org/t/p/w185${this.posterPath}"
    )
}
