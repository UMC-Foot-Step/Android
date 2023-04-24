package com.footstep.dangbal.kotlin.src.login.DataFile

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName(value = "email")val email: String,
    @SerializedName(value = "password")val password: String,
)//{
//    @PrimaryKey(autogenerate = true)
//    var id: Int = 0
//}