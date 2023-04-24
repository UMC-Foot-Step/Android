package com.footstep.dangbal.kotlin.src.main.feed.models

import com.google.gson.annotations.SerializedName
import com.footstep.dangbal.kotlin.config.BaseResponse

data class FeedListResponse(
    @SerializedName("result") val result: ResultFeedList
) : BaseResponse()

