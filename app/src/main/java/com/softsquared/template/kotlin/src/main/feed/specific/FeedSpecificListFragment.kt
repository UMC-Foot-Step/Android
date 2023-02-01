package com.softsquared.template.kotlin.src.main.feed.specific

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentFeedBinding
import com.softsquared.template.kotlin.databinding.FragmentFeedSpecificlistBinding
import com.softsquared.template.kotlin.src.main.feed.FeedListFragment
import com.softsquared.template.kotlin.src.main.feed.info.FeedInfoActivity
import com.softsquared.template.kotlin.src.main.gallery.GalleryFragmentAdater
import com.softsquared.template.kotlin.src.main.gallery.info.GalleryInfoActivity
import com.softsquared.template.kotlin.src.main.gallery.models.PostList
import com.softsquared.template.kotlin.src.main.gallery.models.PostListResponse
import com.softsquared.template.kotlin.src.main.gallery.models.SectionModel

class FeedSpecificListFragment :
    BaseFragment<FragmentFeedSpecificlistBinding>(FragmentFeedSpecificlistBinding::bind, R.layout.fragment_feed_specificlist),
    FeedSpecificListFragmentInterface{


    private lateinit var feedSpecificListFragment: FeedSpecificListFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedSpecificListFragment = this

        /*
            To Do 1. Intent로 유저 인덱스 받아서, 특정 유저 피드 리스트 조회 API 호출
        */

        // 프래그먼트에서 넘겨오는 유저 인덱스 받기
        // 프래그먼트에서 넘겨오는 유저 닉네임 받기
        val userId = arguments?.getInt("userId")
        val userName = arguments?.getString("userName")

        // 프래그먼트 뷰 뿌리기
        binding.feedSpecificlistTitleTxt.text = userName


        // 유저 인덱스로, 특정 유저 피드 리스트 조회 API 호출
        if (userId != null) {
            FeedSpecificListService(this).getSpecificFeedList(userId)
        }


        /*
            To Do 3. 뒤로가기 버튼 클릭 이벤트
         */
        binding.feedspecificlistBtnBack.setOnClickListener {

            // 특정 유저 피드 리스트 조회 프래그먼트로 전환
            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.main_frm, FeedListFragment())
                .commit()
        }






    }


    /*
        To Do 1. 특정 유저 피드 리스트 조회 API 응답 메소드
     */
    override fun onGetSpecificFeedListSuccess(response: PostListResponse) {


        // 게시글 예외처리 - 게시글이 존재하지 않을 떄
        if(response.isSuccess == false){
            response.message?.let { showCustomToast(it) }
        }
        else {

            // 리사이클러 섹션 데이터 구성
            val daySectionFeetStepList = ArrayList<SectionModel>()

            var idx: Int = 0

            // 날짜별 카테고리  갯수만큼 반복
            for(i: Int in 1..response.result.upload_date){

                // 카테고리 별 게시글 리스트 데이터 - ArrayList 객체 생성
                val sectionFeetStepList = ArrayList<PostList>()

                var z: Int = response.result.post_list[idx].posting_cnt
                while(z > 0){

                    sectionFeetStepList.add(
                        response.result.post_list[idx]
                    )
                    idx ++
                    z--
                }

                daySectionFeetStepList.add(
                    SectionModel(
                        response.result.post_list[idx - 1].recordDate,
                        sectionFeetStepList
                    )
                )

            }

            // 리사이클러 뷰 구현
            binding.feedspecificlistRvFootstepList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
//            (layoutManager as LinearLayoutManager).setStackFromEnd(true)
                adapter = FeedSpecificListFragmentAdapter(daySectionFeetStepList, feedSpecificListFragment)
            }

        }

    }
    override fun onGetSpecificFeedListFailure(message: String) {
        showCustomToast(message)
        Log.d("API 연결 에러", message)
    }


    /*
        To Do 2. 특정 피드 게시글 상세 정보 조회 액티비티 전환 메소드
     */
    override fun changeFeedInfoActivity(post_id: Int) {
        val intent = Intent(activity, FeedInfoActivity::class.java)
        intent.putExtra("posting-id", post_id)
        startActivity(intent)
    }

}