package com.softsquared.template.kotlin.src.main.gallery.info

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentGallaryBinding
import com.softsquared.template.kotlin.databinding.FragmentGalleryinfoBinding
import com.softsquared.template.kotlin.src.main.gallery.GalleryFragmentInterface
import com.softsquared.template.kotlin.src.main.gallery.info.models.FeetStepInfoResponse

// GalleryInfoFragment 생성자 = 발자취 게시글 상세 정보 (게시글 정보 & 댓글 리스트)
class GalleryInfoFragment (val feetStepInfoResponse: FeetStepInfoResponse) :
    BaseFragment<FragmentGalleryinfoBinding>(FragmentGalleryinfoBinding::bind, R.layout.fragment_galleryinfo)
    {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
            To Do. feetStepInfoResponse 객체 받아서, Fragment View에 뿌리기
         */

        // To DO 1. 발자취 게시글 상세 정보 뿌리기
        binding.galleryinfoIvPostImg.setImageResource(feetStepInfoResponse.img)
        binding.galleryinfoTvDay.text = feetStepInfoResponse.day
        binding.galleryinfoPostPosition.text = feetStepInfoResponse.position
        binding.galleryinfoTvLikeCnt.text = feetStepInfoResponse.like_cnt.toString()
        binding.galleryinfoTvCommentCnt.text = feetStepInfoResponse.coment_cnt.toString()
        binding.galleryinfoTvPostUsername.text = feetStepInfoResponse.post_username
        binding.galleryinfoTvPostDes.text = feetStepInfoResponse.des



        // To DO 2. 발자취 게시글 댓글 리스트 정보 뿌리기
        // RecyclerView 로 뿌림
        binding.galleryinfoRvComment.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = GalleryInfoFragmentAdapter(feetStepInfoResponse.comment_list)
        }




    }
}
