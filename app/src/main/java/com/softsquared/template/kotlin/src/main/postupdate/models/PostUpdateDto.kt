package com.softsquared.template.kotlin.src.main.postupdate.models

import com.google.gson.annotations.SerializedName

data class PostUpdateDto(
    @SerializedName("address") val postingAddress: String,
    @SerializedName("latitude") val postingLatitude: Double,
    @SerializedName("Longitude") val postingLongitude: Double,
    @SerializedName("name") val postingName: String
)
