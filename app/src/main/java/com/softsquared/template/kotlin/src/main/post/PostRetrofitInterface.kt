package com.softsquared.template.kotlin.src.main.post

import com.softsquared.template.kotlin.src.main.post.models.PostListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface PostRetrofitInterface {
    @Multipart
    @POST("/footstep/write")
    fun postPostList(
        @Part image: MultipartBody.Part?,
        @PartMap data: HashMap<String, RequestBody>
        ) : Call<PostListResponse>
}