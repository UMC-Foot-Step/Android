package com.softsquared.template.kotlin.src.main.gallery.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class PostListByDateResponse(
    @SerializedName("result") val result: ResultPostListByDate
) : BaseResponse()
