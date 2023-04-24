package com.footstep.dangbal.kotlin.src.main.myPage.mypageResultFile

import com.google.gson.annotations.SerializedName

data class Resultmypage (
    @SerializedName("postingCount") val postingCount: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileImageUrl") val url: String,
)

