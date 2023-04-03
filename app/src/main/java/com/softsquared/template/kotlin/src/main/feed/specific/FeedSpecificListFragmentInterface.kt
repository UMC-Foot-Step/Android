package com.softsquared.template.kotlin.src.main.feed.specific

import com.softsquared.template.kotlin.src.main.gallery.models.PostListResponse

interface FeedSpecificListFragmentInterface {

    /*
        To Do 1. 특정 유저 피드 리스트 조회 API
     */
    fun onGetSpecificFeedListSuccess(response: PostListResponse,userId:Int)
    fun onGetSpecificFeedListFailure(message: String)


    /*
        To Do 2. 특정 피드 게시글 상세 조회 액티비티로 전환
    */
    fun changeFeedInfoActivity(post_id: Int,userId:Int)
}