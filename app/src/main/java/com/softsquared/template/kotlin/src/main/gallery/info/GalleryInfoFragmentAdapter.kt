package com.softsquared.template.kotlin.src.main.gallery.info

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ItemGalleryinfoCommentBinding
import com.softsquared.template.kotlin.src.main.gallery.info.models.ResultCommentList


/*
    특정 발자취 게시글 댓글 리스트 조회
 */
class GalleryInfoFragmentAdapter(
    private val resultCommentList: ArrayList<ResultCommentList>
) : RecyclerView.Adapter<GalleryInfoFragmentAdapter.MyViewHolder>() {

    class MyViewHolder(
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


            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
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
