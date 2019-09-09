package com.example.feature.details.data.model

import com.google.gson.annotations.SerializedName

data class Company(
    val id: Int?,
    val name: String?,
    @SerializedName("logo_path") val logoPath: String?,
    @SerializedName("origin_country") val originCountry: String?
)
