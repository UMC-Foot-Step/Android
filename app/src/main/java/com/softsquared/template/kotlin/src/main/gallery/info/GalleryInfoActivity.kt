package com.softsquared.template.kotlin.src.main.gallery.info

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityGalleryinfoBinding
import com.softsquared.template.kotlin.src.main.gallery.info.models.FeetStepInfoResponse
import com.softsquared.template.kotlin.src.main.map.MapFragment


class GalleryInfoActivity()
    : BaseActivity<ActivityGalleryinfoBinding>(ActivityGalleryinfoBinding::inflate),
    GalleryInfoActivityInterface{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // GalleryFragment 에서 전달 받은 데이터객체 받기
        val intent = intent
        val feetStepInfoResponse: FeetStepInfoResponse
        feetStepInfoResponse = intent.getSerializableExtra("feetStepInfoResponse") as FeetStepInfoResponse

        // 전달받은 데이터객체에서 게시글 Idx로 특정 발자취 게시글 조회
            // 프론트 리소스 상 API 요청은 생략
            // 로직만 비슷하게 설계해둠
        // GalleryInfoService(this).getPostInfo(postIdx)
        onGetPostInfoSuccess(feetStepInfoResponse)

        supportFragmentManager.beginTransaction().replace(com.softsquared.template.kotlin.R.id.galleryinfo_frm, GalleryInfoFragment(feetStepInfoResponse)).commitAllowingStateLoss()


    }


    // 게시글 Idx로 특정 발자취 게시글 정보 상세 조회 API 요청 값 받아서,
    // Activity View 구현
    override fun onGetPostInfoSuccess(response: FeetStepInfoResponse) {
        binding.galleryTvTitle.text = response.title

        // Fragment에서 뿌릴 데이터 전달
    }

    override fun onGetPostInfoFailure(message: String) {
    }


    // Fragment로 API 응답 데이터 전달
    private fun setupFragemntLayout(response: FeetStepInfoResponse){
        supportFragmentManager.beginTransaction().replace(com.softsquared.template.kotlin.R.id.galleryinfo_frm, GalleryInfoFragment(response)).commitAllowingStateLoss()
    }

}