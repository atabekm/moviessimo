package com.example.feature.details.data.model

import com.google.gson.annotations.SerializedName

data class Language(
    val name: String?,
    @SerializedName("iso_639_1") val isoCode: String?
)
