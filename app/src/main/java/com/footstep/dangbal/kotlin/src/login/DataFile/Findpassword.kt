package com.footstep.dangbal.kotlin.src.login.DataFile

import com.google.gson.annotations.SerializedName

data class Findpassword(
    @SerializedName(value = "email")val email: String,
    @SerializedName(value = "username")val username: String,
)
