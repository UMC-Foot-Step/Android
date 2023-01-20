package com.softsquared.template.kotlin.src.main.gallery.info

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.databinding.ItemGallerySubSectionBinding
import com.softsquared.template.kotlin.databinding.ItemGalleryinfoCommentBinding
import com.softsquared.template.kotlin.src.main.gallery.GalleryFragmentInterface
import com.softsquared.template.kotlin.src.main.gallery.info.models.FeetStepInfoResponse
import com.softsquared.template.kotlin.src.main.gallery.info.models.ResultCommentList
import com.softsquared.template.kotlin.src.main.gallery.models.ResultFeetStepList


/*
    특정 발자취 게시글 댓글 리스트 조회
 */
class GalleryInfoFragmentAdapter(
    private val galleryInfoActivity: GalleryInfoActivity,
    private val resultCommentList: ArrayList<ResultCommentList>
) : RecyclerView.Adapter<GalleryInfoFragmentAdapter.MyViewHolder>() {

    class MyViewHolder(
        private val galleryInfoActivity: GalleryInfoActivity,
        private val binding: ItemGalleryinfoCommentBinding,
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(resultCommentList: ResultCommentList) {
            with(binding) {
                galleryinfoTvCommentUsername.text = resultCommentList.username
                galleryinfoTvCommentDes.text = resultCommentList.des
            }


            // 댓글 Item 클릭 이벤트
            binding.root.setOnClickListener{
                Log.d("리사이클러 Item 체크", "data = "+ resultCommentList)
            }

            binding.galleryinfoBtnCommentOption.setOnClickListener {

                /*
                To Do 1. 팝업메뉴 구현
                */
                // Activity 특성을 인자로 받기에, GallertInfoActivity 객체를 받아옴.
                val popup = PopupMenu(galleryInfoActivity, binding.galleryinfoBtnCommentOption)
                galleryInfoActivity.menuInflater.inflate(R.menu.menu_galleryinfo_option_btn, popup.menu)

                popup.setOnMenuItemClickListener {
                        item ->
                    when(item.itemId){
                        R.id.btn_flag ->
                            galleryInfoActivity.showCustomToast("신고하기")
                        R.id.btn_edit ->
                            galleryInfoActivity.showCustomToast("수정하기")
                        R.id.btn_del ->
                            galleryInfoActivity.showCustomToast("삭제하기")
                        }
                        false
                    }
                    popup.show()

                }

            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(galleryInfoActivity,
            ItemGalleryinfoCommentBinding.inflate(
                LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d("포지션 값 체크", "Postion = $position")
        holder.bind(resultCommentList[position])
    }

    override fun getItemCount() = resultCommentList.size

}
