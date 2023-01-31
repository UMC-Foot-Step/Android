package com.softsquared.template.kotlin.src.main.feed.models

import com.google.gson.annotations.SerializedName

data class ResultFeedList(
    @SerializedName("feedListDto") val feedListDto: ArrayList<FeedList>,
    @SerializedName("postingCount") val postingCount: Int
)
