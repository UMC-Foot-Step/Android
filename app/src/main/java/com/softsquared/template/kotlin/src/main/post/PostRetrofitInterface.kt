package com.softsquared.template.kotlin.src.main.post

import com.softsquared.template.kotlin.src.main.post.models.PostListResponse
import retrofit2.Call
import retrofit2.http.*

interface PostRetrofitInterface {
    @POST("/footstep/write")
    fun postTextList(
        // @Header("Authorization") Authorization: String,
        @Body params: HashMap<String, Any>
    ) : Call<PostListResponse>
    /*
    fun postPostList(
        //@Header("token") token: String,
        @Part image: MultipartBody.Part?,
        @Part("content") content: MultipartBody.Part,
        @Part("recordDate") recordDate: MultipartBody.Part,
        @Part("title") title: MultipartBody.Part,
        @Part("visibilityStatusCode") visibilityStatusCode: MultipartBody.Part,
        @PartMap(encoding = "createPlaceDto") createPlaceDto: HashMap<String, RequestBody>
        ) : Call<PostListResponse>
    * */

}