package com.footstep.dangbal.kotlin.src.main.gallery.info


import android.util.Log
import com.footstep.dangbal.kotlin.config.ApplicationClass
import com.footstep.dangbal.kotlin.config.BaseResponse
import com.footstep.dangbal.kotlin.src.main.feed.models.ReportResponse
import com.footstep.dangbal.kotlin.src.main.feed.models.createReportDto
import com.footstep.dangbal.kotlin.src.main.gallery.GalleryRetrofitInterface
import com.footstep.dangbal.kotlin.src.main.gallery.info.models.CommentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GalleryInfoFragmentService(val galleryInfoFragmentInterface: GalleryInfoFragmentInterface) {

    /*
    To Do 3. 게시글 댓글 삭제 API-문제
    */
    fun deletePostComment(comment_id: Int) {
        val GalleryRetrofitInterface =
            ApplicationClass.sRetrofit.create(GalleryRetrofitInterface::class.java)
        Log.d("reportProcess", "갤러리 댓글삭제 서비스 comment_id : $comment_id")

        GalleryRetrofitInterface.deletePostComment(comment_id).enqueue(object :
            Callback<CommentResponse> {
            override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
                Log.d("reportProcess", "갤러리 댓글 삭제 서비스성공 ${response.body()}  isSuccessful : ${response.isSuccessful} code : ${response.code()}")

                galleryInfoFragmentInterface.onDeletePostCommentSuccess(response.body() as CommentResponse)
            }

            override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                galleryInfoFragmentInterface.onDeletePostCommentFailure(t.message ?: "통신 오류")
            }
        })
    }

    //댓글 신고 API
    fun ReportComment(comment_id:Int,createReportDto:createReportDto){
        val GalleryRetrofitInterface =
            ApplicationClass.sRetrofit.create(GalleryRetrofitInterface::class.java)
        Log.d("reportProcess", "갤러리 댓글 ReportComment 안쪽직전진입")

        GalleryRetrofitInterface.reportComment(comment_id,createReportDto).enqueue(object :
            Callback<ReportResponse> {

            override fun onResponse(
                call: Call<ReportResponse>,
                response: Response<ReportResponse>
            ) {
                galleryInfoFragmentInterface.onReportCommentSuccess(response.body() as ReportResponse)
                Log.d("reportProcess", "갤러리 댓글 ReportComment onResponse")

            }

            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                galleryInfoFragmentInterface.onReportCommentFailure(t.message ?: "통신 오류")
                Log.d("reportProcess", "갤러리 댓글 ReportComment onFailure")

            }

        })
    }

    //유저 신고 API-문제
    fun ReportUser(user_id:Int,createReportDto:createReportDto){
        val GalleryRetrofitInterface =
            ApplicationClass.sRetrofit.create(GalleryRetrofitInterface::class.java)
        Log.d("reportProcess", "갤러리 댓글유저 서비스 유저아이디 : $user_id crDto : $createReportDto")

        GalleryRetrofitInterface.reportUser(user_id,createReportDto).enqueue(object :
            Callback<ReportResponse> {
            //Log.d("reportProcess", "갤러리 댓글유저신고 서비스 성공 responseresponse.code() : ${response.code()}")

            override fun onResponse(
                call: Call<ReportResponse>,
                response: Response<ReportResponse>
            ) {
                galleryInfoFragmentInterface.onReportUserSuccess(response.body() as ReportResponse)
                Log.d("reportProcess", "갤러리 댓글유저신고 서비스 성공 response.body() : ${response.body()} response.code() : ${response.code()}" +
                        "isSuccess : ${response.isSuccessful}")

            }

            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                galleryInfoFragmentInterface.onReportUserFailure(t.message ?: "통신 오류")
                Log.d("reportProcess", "갤러리 댓글유저 ReportUser onFailure")

            }

        })
    }
}