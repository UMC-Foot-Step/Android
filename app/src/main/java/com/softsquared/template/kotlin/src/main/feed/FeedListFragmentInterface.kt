package com.softsquared.template.kotlin.src.main.feed

import com.softsquared.template.kotlin.src.main.feed.models.FeedListResponse
import com.softsquared.template.kotlin.src.main.feed.models.ResultFeedList

interface FeedListFragmentInterface {

    /*
        To Do 1. 타 유저 피드 리스트 조회 API
     */
    fun onGetFeedListSuccess(feedListService: FeedListResponse)
    fun onGetFeedListFailure(message: String)

    /*
        To Do 2. FeedInfoActivity로 전환
     */
    fun changeFeedInfoActivity(postIdx: Int)


}