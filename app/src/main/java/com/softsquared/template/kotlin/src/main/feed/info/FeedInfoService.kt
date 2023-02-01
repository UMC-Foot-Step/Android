package com.softsquared.template.kotlin.src.main.feed.info

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.src.main.gallery.GalleryRetrofitInterface
import com.softsquared.template.kotlin.src.main.gallery.info.models.PostCommentRequest
import com.softsquared.template.kotlin.src.main.gallery.info.models.PostInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedInfoService(val feedInfoActivityInterface: FeedInfoActivityInterface) {


    /*
    To Do 1. 발자취 상세 정보 조회 API 엮기
    */
    fun getPostInfo(posting_id: Int){

        val GalleryRetrofitInterface = ApplicationClass.sRetrofit.create(GalleryRetrofitInterface::class.java)
        GalleryRetrofitInterface.getGalleryPostInfo(posting_id).enqueue(object :
            Callback<PostInfoResponse> {
            override fun onResponse(call: Call<PostInfoResponse>, response: Response<PostInfoResponse>) {
                feedInfoActivityInterface.onGetPostInfoSuccess(response.body() as PostInfoResponse)
            }

            override fun onFailure(call: Call<PostInfoResponse>, t: Throwable) {
                feedInfoActivityInterface.onGetPostInfoFailure(t.message ?: "통신 오류")
            }
        })

    }


    /*
        To Do 2. 좋아요 클릭 API
    */
    fun postPostLike(posting_id: Int) {
        val GalleryRetrofitInterface = ApplicationClass.sRetrofit.create(GalleryRetrofitInterface::class.java)
        GalleryRetrofitInterface.postPostLike(posting_id).enqueue(object :
            Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                // API 요청 성공 후 뷰 업데이트
                getPostInfo(posting_id)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                feedInfoActivityInterface.onDeletePostFailure(t.message ?: "통신 오류")
            }
        })
    }


    /*
        To Do 3. 댓글 작성 API
     */
    fun postPostComment(postRequest: PostCommentRequest, posting_id: Int){
        val GalleryRetrofitInterface = ApplicationClass.sRetrofit.create(GalleryRetrofitInterface::class.java)
        GalleryRetrofitInterface.postPostComment(posting_id, postRequest).enqueue(object :
            Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                // API 요청 성공 후 뷰 업데이트
                getPostInfo(posting_id)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                feedInfoActivityInterface.onDeletePostFailure(t.message ?: "통신 오류")
            }
        })
    }

    /*
        To Do 4. 게시글 댓글 삭제 API
        To Do 4.1 댓글 예외처리 (응답 데이터 토대로)
    */
    fun deletePostComment(comment_id: Int) {
        val GalleryRetrofitInterface =
            ApplicationClass.sRetrofit.create(GalleryRetrofitInterface::class.java)
        GalleryRetrofitInterface.deletePostComment(comment_id).enqueue(object :
            Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                feedInfoActivityInterface.onDeletePostCommentSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                feedInfoActivityInterface.onDeletePostCommentFailure(t.message ?: "통신 오류")
            }
        })
    }


}