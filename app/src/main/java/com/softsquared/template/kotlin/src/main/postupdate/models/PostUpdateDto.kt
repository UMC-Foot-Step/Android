package com.softsquared.template.kotlin.src.main.postupdate.models

import com.google.gson.annotations.SerializedName

data class PostUpdateDto(
    @SerializedName("address") val address: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("name") val name: String
)
