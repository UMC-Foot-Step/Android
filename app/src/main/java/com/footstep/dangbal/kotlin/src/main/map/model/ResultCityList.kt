package com.footstep.dangbal.kotlin.src.main.map.model

import com.google.gson.annotations.SerializedName

data class ResultCityList(
    @SerializedName("placeId") val placeId:Int,
    @SerializedName("placeName")val placeName:String,
    @SerializedName("latitude")val lat:Double,
    @SerializedName("longitude")val lng:Double
    )
