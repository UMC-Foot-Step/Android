package com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile

import com.google.gson.annotations.SerializedName

data class Nickname(
    @SerializedName(value = "nickname")val nickname: String,
)
