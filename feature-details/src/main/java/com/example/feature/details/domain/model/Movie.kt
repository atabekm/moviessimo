package com.example.feature.details.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val genres: String,
    val duration: String,
    val director: String,
    val screenplay: String,
    val cast: String,
    val backdropImage: String,
    val posterImage: String,
    val releaseDate: String,
    val rating: Float
) {
    companion object {
        val EMPTY = Movie(1, "", "", "", "", "", "", "", "", "", "", 0f)
    }
}
