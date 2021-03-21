package com.example.feature.details.data.model

import com.google.gson.annotations.SerializedName

internal data class Country(
    val name: String?,
    @SerializedName("iso_3166_1") val isoCode: String?
)
