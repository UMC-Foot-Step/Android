package com.softsquared.template.kotlin.src.main.gallery

import com.softsquared.template.kotlin.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.src.main.Example.models.PostSignUpRequest
import com.softsquared.template.kotlin.src.main.Example.models.SignUpResponse
import com.softsquared.template.kotlin.src.main.Example.models.UserResponse
import com.softsquared.template.kotlin.src.main.gallery.info.models.PostCommentRequest
import com.softsquared.template.kotlin.src.main.gallery.info.models.PostInfoResponse
import com.softsquared.template.kotlin.src.main.gallery.models.PostListResponse
import retrofit2.Call
import retrofit2.http.*

interface GalleryRetrofitInterface {

    /*
        To Do 1. Gallery - 발자취 리스트 조회 API 호출
     */
    @GET("/footstep/gallery")
    fun getGalleryPostList() : Call<PostListResponse>

    /*
        To Do 2. GalleryInfo - 게시글 상세 정보 조회 API 호출
     */
    @GET("/footstep/posting/{posting-id}")
    fun getGalleryPostInfo(@Path("posting-id") posting_id: Int) : Call <PostInfoResponse>

    /*
        To Do 3. GalleryInfo - 게시글 삭제 API 호출
     */
    @PATCH("footstep/{posting-id}/remove")
    fun deletePost(@Path("posting-id") posting_id: Int) : Call<BaseResponse>


    /*
        To Do 4. GalleryInfo - 게시글 댓글 삭제 API 호출
     */
    @PATCH("footstep/{comment_id}")
    fun deletePostComment(@Path("comment_id") comment_id: Int) : Call<BaseResponse>

    /*
        To Do 5. GalleryInfo - 게시글 좋아요 클릭 API 호출
     */
    @POST("footstep/{posting_id}/like")
    fun postPostLike(@Path("posting_id") posting_id: Int) : Call<BaseResponse>


    /*
        To Do 6. GalleryInfo - 게시글 댓글 작성 API 호출
     */
    @POST("footstep/{posting_id}/comment")
    fun postPostComment(@Path("posting_id") posting_id: Int, @Body params: PostCommentRequest) : Call <BaseResponse>

}
