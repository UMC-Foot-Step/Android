package com.softsquared.template.kotlin.src.main.feed

import com.softsquared.template.kotlin.src.main.feed.models.FeedListResponse
import retrofit2.http.GET
import retrofit2.Call

interface FeedRetrofitInterface {

    /*
        To Do 1. 타 유저 피드 리스트 조회 API
     */
    @GET("/footstep/feed")
    fun getFeedList() : Call<FeedListResponse>


}