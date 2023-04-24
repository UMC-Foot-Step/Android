package com.footstep.dangbal.kotlin.src.main.myPage.mypageResponseFile

import com.google.gson.annotations.SerializedName

data class changeNicknameInfo(
    @SerializedName(value = "nickname")val nickname: String,
)
