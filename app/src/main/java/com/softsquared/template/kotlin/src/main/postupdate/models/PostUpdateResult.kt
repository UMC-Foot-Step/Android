package com.softsquared.template.kotlin.src.main.postupdate.models

import com.google.gson.annotations.SerializedName

data class PostUpdateResult (
    @SerializedName("createPlaceDto") val postPlaceDto: ArrayList<PostUpdateDto>,
    @SerializedName("content") val postingContent: String,
    @SerializedName("imageUrl") val postingImageUrl: String,
    @SerializedName("recordDate") val postingDate: String,
    @SerializedName("title") val postingTitle: String,
    @SerializedName("visibilityStatusCode") val postingOpen: Int
)