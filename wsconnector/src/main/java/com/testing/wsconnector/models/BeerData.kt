package com.testing.wsconnector.models

import com.google.gson.annotations.SerializedName

data class BeerData(
    val id: String,
    val name: String,
    val tagline: String,
    @SerializedName("first_brewed") val firstBrewed: String,
    val description: String,
    @SerializedName("image_url") val imageUrl: String
)