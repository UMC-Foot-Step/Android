package com.softsquared.template.kotlin.src.main.postupdate

import com.softsquared.template.kotlin.src.main.postupdate.models.PostUpdateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/*
    /*
        To Do 2. GalleryInfo - 게시글 상세 정보 조회 API 호출
     */
    @GET("/footstep/posting/{posting-id}")
    fun getGalleryPostInfo(@Path("posting-id") posting_id: Int) : Call <PostInfoResponse>
 */
interface PostUpdateRetrofitInterface {
    // 발자취 수정 데이터 가져오기
    @GET("/footstep/{posting-id}/edit")
    fun getPostUpdateInfo(
        @Header("Authorization") accessToken: String,
        @Path("posting-id") postingId: Int
    ) : Call<PostUpdateResponse>
}