package com.softsquared.template.kotlin.src.main.feed.specific

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.feed.FeedRetrofitInterface
import com.softsquared.template.kotlin.src.main.feed.models.FeedListResponse
import com.softsquared.template.kotlin.src.main.gallery.models.PostListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedSpecificListService(val FeedSpecificListFragmentInterface: FeedSpecificListFragmentInterface) {

    /*
        To Do 1. 특정 유저 피드 리스트 조회 API 호출
    */
    fun getSpecificFeedList(userId: Int){
        val feedRetrofitInterFace =
            ApplicationClass.sRetrofit.create(FeedRetrofitInterface::class.java)
        feedRetrofitInterFace.getSpecificFeedList(userId).enqueue(object : Callback<PostListResponse> {

            // API 요청 성공 시 호출
            override fun onResponse(
                call: Call<PostListResponse>,
                response: Response<PostListResponse>
            ) {
                // 요청 객체 예외처리
                if (response.body() != null) {
                    FeedSpecificListFragmentInterface.onGetSpecificFeedListSuccess(response.body() as PostListResponse)
                } else {
                    Log.d("게시글 Null 에러", response.message())
                }
            }


            // API 요청 실패 시 호출
            override fun onFailure(call: Call<PostListResponse>, t: Throwable) {
                FeedSpecificListFragmentInterface.onGetSpecificFeedListFailure(t.message ?: "통신 오류")
            }
        })
    }
}