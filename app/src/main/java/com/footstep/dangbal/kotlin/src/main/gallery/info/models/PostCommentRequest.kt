package com.footstep.dangbal.kotlin.src.main.gallery.info.models

import com.google.gson.annotations.SerializedName

data class PostCommentRequest(
    @SerializedName("content") val content: String
)
