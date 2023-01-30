package com.softsquared.template.kotlin.src.main.feed.models

import com.google.gson.annotations.SerializedName

data class FeedList(
    @SerializedName("commentCount") val commentCount: Int,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("content") val content: String,
    @SerializedName("likes") val likes: Int,
    @SerializedName("placeName") val placeName: String,
    @SerializedName("postingId") val postingId: Int,
    @SerializedName("recordDate") val recordDate: String,
    @SerializedName("title") val title: String,
    @SerializedName("usersId") val userId: Int,
    @SerializedName("nickname") val nickName: String

)
