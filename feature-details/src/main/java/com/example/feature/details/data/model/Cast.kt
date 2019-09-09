package com.example.feature.details.data.model

import com.google.gson.annotations.SerializedName

data class Cast(
    val id: Int,
    val name: String,
    val character: String,
    val gender: Int,
    val order: Int,
    @SerializedName("cast_id") val castId: Int,
    @SerializedName("credit_id") val creditId: String,
    @SerializedName("profile_path") val profilePath: String
)
