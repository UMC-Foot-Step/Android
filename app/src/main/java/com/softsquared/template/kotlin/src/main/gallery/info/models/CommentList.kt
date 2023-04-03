package com.softsquared.template.kotlin.src.main.gallery.info.models

import com.google.gson.annotations.SerializedName

data class CommentList(
    @SerializedName("nickname") val nickName: String,
    @SerializedName("content") val content: String,
    @SerializedName("commentId") val commentId: Int,
    @SerializedName("usersId") val usersId: Int

)
