package com.softsquared.template.kotlin.src.main.postupdate

import com.softsquared.template.kotlin.src.main.postupdate.models.PostEditResponse
import com.softsquared.template.kotlin.src.main.postupdate.models.PostUpdateResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PostUpdateRetrofitInterface {
    // 발자취 수정 데이터 가져오기
    @GET("/footstep/{posting-id}/edit")
    fun getPostUpdateInfo(
//        @Header("Authorization") accessToken: String,
        @Path("posting-id") postingId: Int
    ) : Call<PostUpdateResponse>

    // 발자취 데이터 수정하기
    @Multipart
    @POST("/footstep/{posting-id}/edit")
    fun postPostUpdateInfo(
        @Part image: MultipartBody.Part?,
        @PartMap data: HashMap<String, RequestBody>,
        @Path("posting-id") postingId: Int
    ) : Call<PostEditResponse>
}