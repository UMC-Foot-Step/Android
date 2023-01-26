package com.softsquared.template.kotlin.src.login.DataFile

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("userIdx") val userIdx: Int,
    @SerializedName("jwt") val jwt: String
)
