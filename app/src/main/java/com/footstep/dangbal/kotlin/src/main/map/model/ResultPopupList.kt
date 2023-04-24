package com.footstep.dangbal.kotlin.src.main.map.model

import com.google.gson.annotations.SerializedName

data class ResultPopupList(
    @SerializedName("imageUrl")val imageUrl:String,
    @SerializedName("name")val loacationName:String,
    @SerializedName("postingCount")val postingCount:Int
    )
