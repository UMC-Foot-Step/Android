package com.footstep.dangbal.kotlin.src.login.DataFile

import com.google.gson.annotations.SerializedName

data class FindPasswordResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: String
)
