package com.example.feature.list.data.model

import com.google.gson.annotations.SerializedName

internal data class DiscoverMovie(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int
)

internal data class Movie(
    val id: Int,
    val title: String,
    val overview: String = "",
    val adult: Boolean = false,
    val popularity: Float = 0f,
    val video: Boolean = false,
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("release_date") val releaseDate: String = "",
    @SerializedName("genre_ids") val genreIds: List<Int> = listOf(),
    @SerializedName("original_title") val originalTitle: String = "",
    @SerializedName("original_language") val originalLanguage: String = "",
    @SerializedName("backdrop_path") val backdropPath: String? = "",
    @SerializedName("vote_count") val voteCount: Int = 0,
    @SerializedName("vote_average") val voteAverage: Float = 0f
)
