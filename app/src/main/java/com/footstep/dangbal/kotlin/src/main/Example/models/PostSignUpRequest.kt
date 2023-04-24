package com.footstep.dangbal.kotlin.src.main.Example.models

import com.google.gson.annotations.SerializedName

data class PostSignUpRequest(
        @SerializedName("email") val email: String,
        @SerializedName("password") val password: String,
        @SerializedName("confirmPassword") val confirmPassword: String,
        @SerializedName("nickname") val nickname: String,
        @SerializedName("phoneNumber") val phoneNumber: String
)