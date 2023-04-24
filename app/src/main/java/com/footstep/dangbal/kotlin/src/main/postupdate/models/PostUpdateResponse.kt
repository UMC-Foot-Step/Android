package com.footstep.dangbal.kotlin.src.main.postupdate.models

import com.google.gson.annotations.SerializedName

data class PostUpdateResponse (
    @SerializedName("code") val code: Int = 0,
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("message") val message: String? = null,
    @SerializedName("result") val result: PostUpdateResult
)