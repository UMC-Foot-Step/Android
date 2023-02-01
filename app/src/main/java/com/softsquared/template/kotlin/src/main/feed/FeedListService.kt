package com.softsquared.template.kotlin.src.main.feed

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.feed.models.FeedListResponse
import com.softsquared.template.kotlin.src.main.gallery.GalleryRetrofitInterface
import com.softsquared.template.kotlin.src.main.gallery.models.PostListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedListService(val feedListFragmentInterface: FeedListFragmentInterface) {

    /*
        To Do 1. 타 유저 피드 리스트 조회 API 호출
     */
    fun getFeedList(){
        val feedRetrofitInterFace =
            ApplicationClass.sRetrofit.create(FeedRetrofitInterface::class.java)
        feedRetrofitInterFace.getFeedList().enqueue(object : Callback<FeedListResponse> {

            // API 요청 성공 시 호출
            override fun onResponse(
                call: Call<FeedListResponse>,
                response: Response<FeedListResponse>
            ) {
                // 요청 객체 예외처리
                if (response.body() != null) {
                    feedListFragmentInterface.onGetFeedListSuccess(response.body() as FeedListResponse)
                } else {
                    Log.d("요청 객체 받기실패", response.body().toString())
                }
            }


            // API 요청 실패 시 호출
            override fun onFailure(call: Call<FeedListResponse>, t: Throwable) {
                feedListFragmentInterface.onGetFeedListFailure(t.message ?: "통신 오류")
            }
        })

    }




}