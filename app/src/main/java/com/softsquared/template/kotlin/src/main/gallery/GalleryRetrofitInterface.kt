package com.softsquared.template.kotlin.src.main.gallery

import com.softsquared.template.kotlin.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.softsquared.template.kotlin.src.main.Example.models.PostSignUpRequest
import com.softsquared.template.kotlin.src.main.Example.models.SignUpResponse
import com.softsquared.template.kotlin.src.main.Example.models.UserResponse
import com.softsquared.template.kotlin.src.main.gallery.info.models.PostInfoResponse
import com.softsquared.template.kotlin.src.main.gallery.models.PostListResponse
import retrofit2.Call
import retrofit2.http.*

interface GalleryRetrofitInterface {

    /*
        To Do 1. 갤러리 발자취 리스트 조회 API 호출
     */
    @GET("/footstep/gallery")
    fun getGalleryPostList() : Call<PostListResponse>

    /*
        To Do 2. 갤러리 게시글 상세 정보 조회 API 호출
     */
    @GET("/footstep/posting/{posting-id}")
    fun getGalleryPostInfo(@Path("posting-id") posting_id: Int) : Call <PostInfoResponse>

}
