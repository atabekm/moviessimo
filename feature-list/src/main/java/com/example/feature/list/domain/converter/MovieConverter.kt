// ktlint-disable filename
package com.example.feature.list.domain.converter

import com.example.feature.list.domain.model.MoviePoster

internal fun com.example.feature.list.data.model.Movie.toDomain(): MoviePoster {
    return MoviePoster(
        id = this.id,
        title = this.title,
        posterImageUrl = "https://image.tmdb.org/t/p/w185${this.posterPath}"
    )
}
