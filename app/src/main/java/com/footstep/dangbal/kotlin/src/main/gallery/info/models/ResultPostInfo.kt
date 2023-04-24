package com.footstep.dangbal.kotlin.src.main.gallery.info.models

import com.google.gson.annotations.SerializedName

data class ResultPostInfo(
    @SerializedName("postingDate") val postingDate: String,
    @SerializedName("postingName") val postingName: String,
    @SerializedName("content") val content: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("placeName") val placeName: String,
    @SerializedName("likeNum") val likeNum: Int,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("commentList") val commentList: ArrayList<CommentList>,
    @SerializedName("commentNum") val commentNum: Int
)
