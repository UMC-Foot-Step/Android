package com.footstep.dangbal.kotlin.src.main.map.model

import com.google.gson.annotations.SerializedName

data class ResultPopupList2(
    @SerializedName("imageUrl")val imageUrl:String,
    @SerializedName("name")val locationName:String,
    @SerializedName("postingCount")val postingCount:Int,
    @SerializedName("placeId") val placeId:Int
    )
