package com.softsquared.template.kotlin.src.main.gallery.info.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse
import java.io.Serializable

data class FeetStepInfoResponse(

    // 발자취 게시글 정보
    @SerializedName("day") val day: String,
    @SerializedName("img") val img: Int,
    @SerializedName("title") val title: String,
    @SerializedName("des") val des: String,
    @SerializedName("like_cnt") val like_cnt: Int,
    @SerializedName("position") val position: String,
    @SerializedName("post_username") val post_username: String,
    @SerializedName("comment_cnt") val coment_cnt: Int,

    // 댓글 리스트
    @SerializedName("comment_list") val comment_list: ArrayList<ResultCommentList>

    ): BaseResponse(), Serializable
