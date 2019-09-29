package com.example.feature.details.domain.converter

import com.example.feature.details.domain.model.Movie

object MovieConverter {

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
            director = movie?.credits?.crew
                ?.firstOrNull { it.job == "Director" }
                ?.name ?: "",
            screenplay = movie?.credits?.crew
                ?.firstOrNull { it.job == "Screenplay" || it.job == "Writer" }
                ?.name ?: "",
            cast = movie?.credits?.cast
                ?.filterNot { it.name.isBlank() }
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
