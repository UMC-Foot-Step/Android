package com.softsquared.template.kotlin.src.main.gallery

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentGallaryBinding
import com.softsquared.template.kotlin.src.main.Example.HomeFragmentInterface
import com.softsquared.template.kotlin.src.main.Example.models.UserResponse
import com.softsquared.template.kotlin.src.main.gallery.models.FeetStepListResponse
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
    override fun onGetPostListSuccess(response: List<SectionModel>) {
        setupRecyclerView(response)
    }

    
    /*
        To Do Later
        API 엮을 때 해당 메소드 구현하여
        API 요청 실패에 대한 예외처리
     */
    override fun onGetPostListFailure(response: String) {


    }




    /*
        To DO 3. RecyclerView 구현
     */
    private fun setupRecyclerView(response: List<SectionModel>){
        binding.galleryRvFeetstepList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = GalleryFragmentAdater(response)
        }
    }




}
