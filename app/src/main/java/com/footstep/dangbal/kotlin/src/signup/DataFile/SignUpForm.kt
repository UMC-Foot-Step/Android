package com.footstep.dangbal.kotlin.src.signup.DataFile

import com.google.gson.annotations.SerializedName

data class SignUpForm(
    @SerializedName(value = "email")val email: String,
    @SerializedName(value = "password")val password: String,
    @SerializedName(value = "nickname")val nickname: String,
    )
