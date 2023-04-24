package com.footstep.dangbal.kotlin.src.main.post.models

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: String
)
