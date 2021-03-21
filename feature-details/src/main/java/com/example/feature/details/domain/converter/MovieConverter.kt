package com.example.feature.details.domain.converter

import com.example.feature.details.domain.model.Movie
import kotlin.math.roundToLong

internal fun com.example.feature.details.data.model.Movie?.toDomain(): Movie {
    return Movie(
        id = this?.id ?: 0,
        title = this?.title ?: "",
        overview = this?.overview ?: "",
        genres = this?.genres
            ?.filterNot {
                it.name == null
            }
            ?.joinToString {
                it.name ?: ""
            } ?: "",
        duration = this?.runtime?.let { "$it minutes" } ?: "",
        director = this?.credits?.crew
            ?.firstOrNull { it.job == "Director" }
            ?.name ?: "",
        screenplay = this?.credits?.crew
            ?.firstOrNull { it.job == "Screenplay" || it.job == "Writer" }
            ?.name ?: "",
        cast = this?.credits?.cast
            ?.filterNot { it.name.isBlank() }
            ?.joinToString {
                it.name
            } ?: "",
        backdropImage = this?.backdropPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
        posterImage = this?.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
        releaseDate = this?.releaseDate ?: "",
        rating = this?.voteAverage?.div(2)?.roundToLong()?.toFloat() ?: 0f
    )
}
