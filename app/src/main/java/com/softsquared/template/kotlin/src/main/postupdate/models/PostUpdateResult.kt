package com.softsquared.template.kotlin.src.main.postupdate.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class PostUpdateResult (
    @SerializedName("content") val postingContent: String,
    @SerializedName("createPlaceDto") val createPlaceDto: PostUpdateDto,
    @SerializedName("imageUrl") val postingImageUrl: String,
    @SerializedName("recordDate") val postingDate: String,
    @SerializedName("title") val postingTitle: String,
    @SerializedName("visibilityStatusCode") val postingOpen: Int
)