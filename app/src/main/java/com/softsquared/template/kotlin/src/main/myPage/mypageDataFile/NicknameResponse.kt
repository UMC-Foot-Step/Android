package com.softsquared.template.kotlin.src.main.myPage.mypageDataFile

import com.google.gson.annotations.SerializedName

class NicknameResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("Result") val result: com.softsquared.template.kotlin.src.login.DataFile.Result
)