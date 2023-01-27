package com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile

import com.google.gson.annotations.SerializedName

data class ProfileResult(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("Result") val result: String,
)
