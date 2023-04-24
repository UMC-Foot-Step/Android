package com.footstep.dangbal.kotlin.src.main.gallery.models

import com.google.gson.annotations.SerializedName
import com.footstep.dangbal.kotlin.config.BaseResponse

data class PostListByDateResponse(
    @SerializedName("result") val result: ResultPostListByDate
) : BaseResponse()
