package com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.src.main.myPage.mypageResultFile.Resultmypage
import retrofit2.Call

data class MypageResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Resultmypage?,
)
