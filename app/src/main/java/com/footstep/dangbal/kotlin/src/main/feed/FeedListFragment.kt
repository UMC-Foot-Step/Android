package com.footstep.dangbal.kotlin.src.main.feed

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.footstep.dangbal.kotlin.R
import com.footstep.dangbal.kotlin.config.BaseFragment
import com.footstep.dangbal.kotlin.databinding.FragmentFeedBinding
import com.footstep.dangbal.kotlin.src.main.feed.info.FeedInfoActivity
import com.footstep.dangbal.kotlin.src.main.feed.models.FeedListResponse

class FeedListFragment :
    BaseFragment<FragmentFeedBinding>(FragmentFeedBinding::bind, R.layout.fragment_feed), FeedListFragmentInterface{

    private lateinit var feedListFragment: FeedListFragment


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        feedListFragment = this


    }

    override fun onStart() {
        super.onStart()
        /*
           To Do 1. 타 유저 피드 리스트 조회 API 호출

        */
        FeedListService(this).getFeedList()

    }

    /*
        To Do 1. 타 유저 피드 리스트 조회 API 호출 응답 메소드
     */
    override fun onGetFeedListSuccess(feedListResponse: FeedListResponse) {

        // 피드 게시글 리스트 예외처리
        if(feedListResponse.isSuccess == false){
            feedListResponse.message?.let { showCustomToast(it) }
        }
        else{
            binding.feedlistRvFeeds.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                adapter = activity?.let {
                    FeedListFragmentAdapter(
                        feedListResponse.result.feedListDto, feedListFragment, it
                    )
                }
            }
        }

    }
    override fun onGetFeedListFailure(message: String) {
        showCustomToast(message)
        Log.d("무슨에러야?", message)
    }


    /*
        To Do 2. FeedInfoActivity 전환 메소드
    */
    override fun changeFeedInfoActivity(postIdx: Int,userId:Int) {
        val intent = Intent(activity, FeedInfoActivity::class.java)
        intent.putExtra("posting-id", postIdx)
        intent.putExtra("userId", userId)

        startActivity(intent)
    }
}