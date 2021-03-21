package com.example.feature.details.data.model

import com.google.gson.annotations.SerializedName

internal data class Crew(
    val id: Int,
    val name: String,
    val gender: Int,
    val job: String,
    val department: String,
    @SerializedName("credit_id") val creditId: String,
    @SerializedName("profile_path") val profilePath: String
)
