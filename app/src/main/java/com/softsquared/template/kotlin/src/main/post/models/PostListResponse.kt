package com.softsquared.template.kotlin.src.main.post.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class PostListResponse(
    @SerializedName("result") val result: String
) : BaseResponse()
