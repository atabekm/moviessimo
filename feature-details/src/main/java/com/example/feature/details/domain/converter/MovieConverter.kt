package com.example.feature.details.domain.converter

import com.example.feature.details.domain.model.Movie

object MovieConverter {
    private const val CAST_THRESHOLD = 5

    fun fromDataToDomain(movie: com.example.feature.details.data.model.Movie?): Movie {
        return Movie(
            id = movie?.id ?: 0,
            title = movie?.title ?: "",
            overview = movie?.overview ?: "",
            genres = movie?.genres
                ?.filterNot {
                    it.name == null
                }
                ?.joinToString {
                    it.name ?: ""
                } ?: "",
            duration = "${movie?.runtime} minutes",
            cast = movie?.credits?.cast
                ?.filterNot { it.name.isBlank() }
                ?.take(CAST_THRESHOLD)
                ?.joinToString {
                    it.name
                } ?: "",
            backdropImage = "https://image.tmdb.org/t/p/w500${movie?.backdropPath}",
            posterImage = "https://image.tmdb.org/t/p/w500${movie?.posterPath}",
            releaseDate = movie?.releaseDate ?: "",
            rating = movie?.voteAverage?.div(2)?.toFloat() ?: 0f
        )
    }
}
