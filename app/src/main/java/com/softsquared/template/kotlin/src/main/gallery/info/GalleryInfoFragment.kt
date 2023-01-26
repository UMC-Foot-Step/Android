package com.softsquared.template.kotlin.src.main.gallery.info

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentGalleryinfoBinding
import com.softsquared.template.kotlin.src.main.gallery.info.models.PostInfoResponse
import com.softsquared.template.kotlin.src.main.gallery.info.models.ResultPostInfo
import com.softsquared.template.kotlin.src.main.gallery.info.models_sample.FeetStepInfoResponse

// GalleryInfoFragment 생성자 = 발자취 게시글 상세 정보 (게시글 정보 & 댓글 리스트)
class GalleryInfoFragment (
    val galleryInfoActivity: GalleryInfoActivity,
    val resultPostInfo: ResultPostInfo
) :
    BaseFragment<FragmentGalleryinfoBinding>(FragmentGalleryinfoBinding::bind, R.layout.fragment_galleryinfo)
    {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
            To Do. feetStepInfoResponse 객체 받아서, Fragment View에 뿌리기
         */

        // To DO 1. 발자취 게시글 상세 정보 뿌리기
        Glide.with(binding.root.context).load(resultPostInfo.imageUrl).into(binding.galleryinfoIvPostImg)
        binding.galleryinfoTvDay.text = resultPostInfo.postingDate
        binding.galleryinfoPostPosition.text = resultPostInfo.placeName
        binding.galleryinfoTvLikeCnt.text = resultPostInfo.likeNum.toString()
        binding.galleryinfoTvCommentCnt.text = resultPostInfo.commentNum.toString()
        binding.galleryinfoTvPostUsername.text = resultPostInfo.nickName
        binding.galleryinfoTvPostDes.text = resultPostInfo.content



        // To DO 2. 발자취 게시글 댓글 리스트 정보 뿌리기
        // RecyclerView 로 뿌림
        binding.galleryinfoRvComment.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = GalleryInfoFragmentAdapter(galleryInfoActivity,resultPostInfo.commentList)

            // RecyclerView Item 구분선 넣기
                addItemDecoration(
                    DividerItemDecoration(binding.root.context,
                        DividerItemDecoration.VERTICAL)
                )
        }




    }
}
