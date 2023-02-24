package com.softsquared.template.kotlin.src.main.gallery.map

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMapgalleryBinding
import com.softsquared.template.kotlin.src.main.gallery.info.GalleryInfoActivity
import com.softsquared.template.kotlin.src.main.gallery.models.PostList
import com.softsquared.template.kotlin.src.main.gallery.models.PostListResponse
import com.softsquared.template.kotlin.src.main.gallery.models.SectionModel
import com.softsquared.template.kotlin.src.main.post.PostActivity


class MapGalleryActivity : BaseActivity<ActivityMapgalleryBinding>(ActivityMapgalleryBinding::inflate),
    MapGalleryActivityInterface {

    private var placeId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /*
            To Do 1. 장소 Idx 받아서, 해당 장소에 대한 발자취 게시글 리스트 조회
         */


        /*
            To Do 1.1 장소 Idx 받기
        */
        val intent = intent
        placeId = intent.getIntExtra("placeId",0)

        // 응답 데이터 구성
        // API 요청
        MapGalleryService(this).onGetPostListByPosition(placeId)

        /*
            To Do 4. 발자취 기록하기 액티비티로 전환
         */
        binding.mapgalleryBtnPost.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }

    }


    /*
        To Do 2. 해당 장소에 대한 게시글 리스트 조회 API 호출
     */
    // 동일 장소별 발자취 게시글 리스트 조회 API 요청 성공
    // 응답 데이터를 이용해 RecyclerView 구현
    override fun onGetPostListByPositionSuccess(response: PostListResponse, mapGalleryActivityInterface: MapGalleryActivityInterface){

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

        binding.mapgalleryTvPosition.text = response.result.post_list[0].placeName

        binding.mapgalleryRvPostList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = MapGalleryActivityAdapter(daySectionFeetStepList, mapGalleryActivityInterface)
        }
    }

    // 동일 장소별 발자취 게시글 리스트 조회 API 요청 실패
    override fun onGetPostListByPositionFailure(response: String) {
        Log.d("API 요청 에러  발생 ", response)
        showCustomToast(response)

    }






    /*
        To Do 3. 특정 게시글 클릭 시 해당 게시글 상세 정보 조회 뷰로 전환
     */
    // RecyclerView의 특정 발자취 게시글 클릭 시
    // 특정 발자취 게시글 상세 정보 조회 Activity로 전환
    override fun changeGalleryInfoActivity(posting_id: Int) {
        val intent = Intent(this, GalleryInfoActivity::class.java)
//        val bundle = Bundle()
//        bundle.putSerializable("feetStepInfoResponse", feetStepInfoResponse)
        intent.putExtra("posting-id", posting_id)
        startActivity(intent)
    }

}