package com.softsquared.template.kotlin.src.main.post

import com.softsquared.template.kotlin.src.main.post.models.PostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PostRetrofitInterface {
    @Multipart
    @POST("/footstep/write")
    /*
    fun postInfoList(
        // @Header("Authorization") Authorization: String,
        @Part image: MultipartBody.Part?,
        @PartMap data: HashMap<String, RequestBody>
    ) : Call<PostResponse>

     */
    suspend fun postInfoList(
        // @Header("Authorization") Authorization: String,
        @Part image: MultipartBody.Part?,
        @PartMap data: HashMap<String, RequestBody>
    ) : PostResponse
}