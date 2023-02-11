package com.softsquared.template.kotlin.src.main.map.model

import com.google.gson.annotations.SerializedName

data class allPlaceDto(
    @SerializedName("allPlaceDto") val allPlaceDto: ArrayList<ResultAllPlaceDto>
    )
