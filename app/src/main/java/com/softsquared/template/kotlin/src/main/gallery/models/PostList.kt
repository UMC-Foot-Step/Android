package com.softsquared.template.kotlin.src.main.gallery.models

import com.google.gson.annotations.SerializedName


// 갤러리 정보 조회 데이터 객체
data class PostList(
    @SerializedName("placeName") val placeName: String,
    @SerializedName("recordDate") val recordDate: Int,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("title") val title: String ,
    @SerializedName("likes") val likes_cnt: Int,
    @SerializedName("postingCount") val posting_cnt: Int,
    @SerializedName("postingId") val postingId: Int

)
