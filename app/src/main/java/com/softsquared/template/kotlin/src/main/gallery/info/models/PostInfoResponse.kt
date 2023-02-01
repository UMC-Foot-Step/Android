package com.softsquared.template.kotlin.src.main.gallery.info.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class PostInfoResponse(
    @SerializedName("result") val result: ResultPostInfo
) : BaseResponse()
