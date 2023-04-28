package com.footstep.dangbal.kotlin.src.main.gallery.info.models

import com.google.gson.annotations.SerializedName
import com.footstep.dangbal.kotlin.config.BaseResponse

data class CommentResponse(
    @SerializedName("result") val result: String? = null
) : BaseResponse()
