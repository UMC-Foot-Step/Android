package com.softsquared.template.kotlin.src.main.post.models

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Multipart

/*
발자취 생성하기 데이터 객체
 */

data class PostList(
    @SerializedName("image") val image: MultipartBody.Part,
    @SerializedName("content") val content: String,
    @SerializedName("postingPlaceDto") val placeList: ArrayList<PostPlaceList>,
    @SerializedName("recordDate") val date: String,
    @SerializedName("title") val title: String,
    @SerializedName("visibilityStatusCode") val open: Int
)

data class PostPlaceList(
    @SerializedName("address") val address: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitute") val longitude: Double,
    @SerializedName("name") val placeName: String,
)
