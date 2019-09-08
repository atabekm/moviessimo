package com.example.feature.details.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val adult : Boolean?,
    val id : Int?,
    val budget : Int?,
    val genres : List<Genres>?,
    val homepage : String?,
    val overview : String?,
    val popularity : Double?,
    val revenue : Int?,
    val runtime : Int?,
    val status : String?,
    val tagline : String?,
    val title : String?,
    val video : Boolean?,
    val credits: Credit?,
    @SerializedName("backdrop_path") val backdropPath : String?,
    @SerializedName("belongs_to_collection") val belongsToCollection : String?,
    @SerializedName("imdb_id") val imdbId : String?,
    @SerializedName("original_language") val originalLanguage : String?,
    @SerializedName("original_title") val originalTitle : String?,
    @SerializedName("poster_path") val posterPath : String?,
    @SerializedName("production_companies") val productionCompanies : List<Company>?,
    @SerializedName("production_countries") val productionCountries : List<Country>?,
    @SerializedName("release_date") val releaseDate : String?,
    @SerializedName("spoken_languages") val spokenLanguages : List<Language>?,
    @SerializedName("vote_average") val voteAverage : Double?,
    @SerializedName("vote_count") val voteCount : Int?
)