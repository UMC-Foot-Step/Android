package com.footstep.dangbal.kotlin.src.login.DataFile

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("grantType") val grantType: String,
    @SerializedName("accessToken") val jwt: String,
    @SerializedName("refreshToken") val refreshJwt: String,
)
