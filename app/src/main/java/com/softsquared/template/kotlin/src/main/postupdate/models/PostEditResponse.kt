package com.softsquared.template.kotlin.src.main.postupdate.models

import com.google.gson.annotations.SerializedName

data class PostEditResponse(
    @SerializedName("code") val code: Int = 0,
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("message") val message: String? = null,
    @SerializedName("result") val result: String? = null
)
