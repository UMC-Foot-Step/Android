package com.softsquared.template.kotlin.src.main.gallery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentGallaryBinding
import com.softsquared.template.kotlin.src.main.gallery.info.GalleryInfoActivity
import com.softsquared.template.kotlin.src.main.gallery.map.MapGalleryActivity
import com.softsquared.template.kotlin.src.main.gallery.models.PostList
import com.softsquared.template.kotlin.src.main.gallery.models.PostListResponse
import com.softsquared.template.kotlin.src.main.gallery.models.SectionModel


class GalleryFragment :
    BaseFragment<FragmentGallaryBinding>(FragmentGallaryBinding::bind, R.layout.fragment_gallary),
    GalleryFragmentInterface {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)






        /*
            To Do 1. 갤러리 뷰 형태로 발자취 리스트 조회 구현
            1. 서비스한테 발자취 리스트 더미데이터 생성 후 응답 받는다.
            2. 응답 받은 더미데이터를 가지고 onGetPostListSuccess() 메소드 호출하여
            해당 프래그먼트의 Recycler View 형태로 구현한다.
         */

        GalleryService(this).GetPostList()


    }

    /*
    To Do 2. 갤러리 뷰 형태로 발자취 리스트 조회 구현

    1. 받은 응답 더미 데이터를 가지고 RecyclerView로 구현한다.
    */
//    override fun onGetPostListSuccess(response: List<SectionModel>) {
//        setupRecyclerView(response, this)
//    }

    
    /*
        To Do Later
        API 엮을 때 해당 메소드 구현하여
        API 요청 실패에 대한 예외처리
     */
    override fun onGetPostListFailure(response: String) {
        showCustomToast(response)
        Log.d("API 연결 에러", response)

    }




    /*
        To DO 3. RecyclerView 구현
     */
    private fun setupRecyclerView(response: List<SectionModel>, galleryFragmentInterface: GalleryFragmentInterface){
        binding.galleryRvFeetstepList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = GalleryFragmentAdater(response, galleryFragmentInterface)
        }
    }



    /*
        To DO 4. 프래그먼트 -> GalleryInfoActivity로 전환
        리사이클러 뷰의 특정 Item 클릭 후
        특정 Item 조회 Activity
     */
    override fun changeGalleryInfoActivity(post_idx: Int){
        val intent = Intent(activity, GalleryInfoActivity::class.java)
//        val bundle = Bundle()
//        bundle.putSerializable("feetStepInfoResponse", feetStepInfoResponse)
        intent.putExtra("posting-id", post_idx)
        startActivity(intent)
    }




    /*
        특정 장소 발자취 게시글 리스트 조회 액티비티 전환 메소드
     */
    override fun testChangeMapGalleryActivity(){
        val intent = Intent(activity, MapGalleryActivity::class.java)
        startActivity(intent)
    }



    /*
        To DO 5. 갤러리 게시글 리스트 조회 API 연결
    */
    override fun onGetGalleryPostListSuccess(response: PostListResponse) {
        /*
        날짜별 카테고리로 게시글 그룹화 - 데이터 전처리?
        */
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

        setupRecyclerView(daySectionFeetStepList, this)

    }


}

