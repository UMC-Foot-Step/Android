package com.footstep.dangbal.kotlin.src.main.feed.info

import android.util.Log
import com.footstep.dangbal.kotlin.config.ApplicationClass
import com.footstep.dangbal.kotlin.config.BaseResponse
import com.footstep.dangbal.kotlin.src.main.feed.FeedRetrofitInterface
import com.footstep.dangbal.kotlin.src.main.feed.models.ReportResponse
import com.footstep.dangbal.kotlin.src.main.feed.models.createReportDto
import com.footstep.dangbal.kotlin.src.main.gallery.GalleryRetrofitInterface
import com.footstep.dangbal.kotlin.src.main.gallery.info.models.PostCommentRequest
import com.footstep.dangbal.kotlin.src.main.gallery.info.models.PostInfoResponse
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

    // To Do 5. 댓글 신고하기
    // To Do 5.1 댓글 - 댓글 신고하기
    fun ReportComment(comment_id:Int,createReportDto: createReportDto){
        val GalleryRetrofitInterface =
            ApplicationClass.sRetrofit.create(GalleryRetrofitInterface::class.java)
        Log.d("reportProcess", "ReportComment 안쪽직전진입")

        GalleryRetrofitInterface.reportComment(comment_id,createReportDto).enqueue(object :
            Callback<ReportResponse> {

            override fun onResponse(
                call: Call<ReportResponse>,
                response: Response<ReportResponse>
            ) {
                feedInfoActivityInterface.onReportCommentSuccess(response.body() as ReportResponse)
                Log.d("reportProcess", "ReportComment onResponse")

            }

            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                feedInfoActivityInterface.onReportCommentFailure(t.message ?: "통신 오류")
                Log.d("reportProcess", "ReportComment onFailure")

            }

        })
    }
    
    
    // To Do 5.2 댓글 - 유저 신고하기
    //유저 신고 API
    fun ReportUser(user_id:Int,createReportDto:createReportDto){
        val GalleryRetrofitInterface =
            ApplicationClass.sRetrofit.create(GalleryRetrofitInterface::class.java)
        Log.d("reportProcess", "ReportComment 안쪽직전진입")

        GalleryRetrofitInterface.reportUser(user_id,createReportDto).enqueue(object :
            Callback<ReportResponse> {

            override fun onResponse(
                call: Call<ReportResponse>,
                response: Response<ReportResponse>
            ) {
                feedInfoActivityInterface.onReportUserSuccess(response.body() as ReportResponse)
                Log.d("reportProcess", "ReportComment onResponse")

            }

            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                feedInfoActivityInterface.onReportUserFailure(t.message ?: "통신 오류")
                Log.d("reportProcess", "ReportComment onFailure")

            }

        })
    }

    // To Do 6. 게시글 신고하기

    fun ReportPost(createReportDto: createReportDto,posting_id: Int){
        val feedRetrofitInterFace =
            ApplicationClass.sRetrofit.create(FeedRetrofitInterface::class.java)
        feedRetrofitInterFace.ReportPost(createReportDto,posting_id).enqueue(object : Callback<ReportResponse> {
            override fun onResponse(
                call: Call<ReportResponse>,
                response: Response<ReportResponse>
            ) {
                Log.d("reportProcess", "ReportPost onResponse")

                feedInfoActivityInterface.onReportPostSuccess(response.body() as ReportResponse)
            }

            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                Log.d("reportProcess", "ReportPost onFailure")

                feedInfoActivityInterface.onReportPostFailure(t.message ?: "통신 오류")
            }
        })
    }


}