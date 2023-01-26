package com.softsquared.template.kotlin.src.main.gallery.map

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMapgalleryBinding
import com.softsquared.template.kotlin.src.main.gallery.info.GalleryInfoActivity
import com.softsquared.template.kotlin.src.main.gallery.info.models_sample.FeetStepInfoResponse
import com.softsquared.template.kotlin.src.main.gallery.models_sample.SectionModel

class MapGalleryActivity: BaseActivity<ActivityMapgalleryBinding>(ActivityMapgalleryBinding::inflate),
    MapGalleryActivityInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /*
            To Do 1. 위치 데이터 받아서, 해당 장소에 대한 발자취 게시글 리스트 조회
         */

        // API 요청 & 응답 데이터 구성
        MapGalleryService(this).onGetPostListByPosition()


    }

    // 동일 장소별 발자취 게시글 리스트 조회 API 요청 성공
    // 응답 데이터를 이용해 RecyclerView 구현
    override fun onGetPostListByPositionSuccess(response: ArrayList<SectionModel>, mapGalleryActivityInterface: MapGalleryActivityInterface){
        binding.mapgalleryTvPosition.text = response[0].day_post_list[0].position

        binding.mapgalleryRvPostList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = MapGalleryActivityAdapter(response, mapGalleryActivityInterface)
        }
    }

    // 동일 장소별 발자취 게시글 리스트 조회 API 요청 실패
    override fun onGetPostListByPositionFailure(response: ArrayList<SectionModel>) {

    }


    // RecyclerView의 특정 발자취 게시글 클릭 시
    // 특정 발자취 게시글 상세 정보 조회 Activity로 전환
    override fun changeGalleryInfoActivity(response: FeetStepInfoResponse) {
        val intent = Intent(this, GalleryInfoActivity::class.java)
//        val bundle = Bundle()
//        bundle.putSerializable("feetStepInfoResponse", feetStepInfoResponse)
        intent.putExtra("feetStepInfoResponse", response)
        startActivity(intent)
    }

}