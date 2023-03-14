package com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile

import com.google.gson.annotations.SerializedName

data class changePasswordInfo(
    @SerializedName(value = "changedPassword")val changedPassword: String,
    @SerializedName(value = "currentPassword")val currentPassword: String,
)
