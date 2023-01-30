package com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.src.main.myPage.NicknameService

data class changeNicknameInfo(
    @SerializedName(value = "nickname")val nickname: String,
)
