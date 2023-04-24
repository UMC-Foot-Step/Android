package com.footstep.dangbal.kotlin.src.main.myPage.mypageResponseFile


import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: String
)