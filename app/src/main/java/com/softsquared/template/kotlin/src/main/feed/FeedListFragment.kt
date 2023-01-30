package com.softsquared.template.kotlin.src.main.feed

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentFeedBinding
import com.softsquared.template.kotlin.src.main.feed.models.FeedList
import com.softsquared.template.kotlin.src.main.feed.models.FeedListResponse
import com.softsquared.template.kotlin.src.main.feed.models.ResultFeedList
import com.softsquared.template.kotlin.src.main.gallery.info.GalleryInfoFragmentAdapter

class FeedListFragment :
    BaseFragment<FragmentFeedBinding>(FragmentFeedBinding::bind, R.layout.fragment_feed), FeedListFragmentInterface{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /*
            To Do 1. 타 유저 피드 리스트 조회 API 호출
         */
        FeedListService(this).getFeedList()




    }

    /*
        타 유저 피드 리스트 조회 API 호출 응답 메소드
     */
    override fun onGetFeedListSuccess(feedListResponse: FeedListResponse) {
        binding.feedlistRvFeeds.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = FeedListFragmentAdapter(
                feedListResponse.result.feedListDto
            )
        }
    }

    override fun onGetFeedListFailure(message: String) {
        showCustomToast(message)
        Log.d("무슨에러야?", message)
    }
}