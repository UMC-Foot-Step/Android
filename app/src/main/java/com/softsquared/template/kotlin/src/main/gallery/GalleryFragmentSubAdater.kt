package com.softsquared.template.kotlin.src.main.gallery

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ItemGallerySubSectionBinding
import com.softsquared.template.kotlin.src.main.gallery.models.ResultFeetStepList


/*
    날짜별 카테고리로 게시글 리스트 갯수만큼 조회
 */
class GalleryFragmentSubAdater(
    private val resultFeetStepList: ArrayList<ResultFeetStepList>,
    val galleryFragmentInterface: GalleryFragmentInterface
) : RecyclerView.Adapter<GalleryFragmentSubAdater.MyViewHolder>() {

    class MyViewHolder(
        private val binding: ItemGallerySubSectionBinding,
        private val galleryFragmentInterface: GalleryFragmentInterface
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(resultFeetStep: ResultFeetStepList) {
            with(binding) {
                galleryIvPostimg.setImageResource(resultFeetStep.img)
                galleryTvPostTitle.text = resultFeetStep.title
                galleryTvPostlikeCnt.text = resultFeetStep.like_cnt.toString()
                galleryTvPostPosition.text = resultFeetStep.position
            }
            binding.root.setOnClickListener{
                val pos: Int = getAdapterPosition()
                Log.d("리사이클러 뷰 포지션 값 체크", "position = "+pos)
                Log.d("리사이클러 Item 체크", "data = "+ resultFeetStep)
//                galleryFragmentInterface.changeGalleryInfoActivity()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemGallerySubSectionBinding.inflate(LayoutInflater.from(parent.context),
            parent, false), galleryFragmentInterface)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d("포지션 값 체크", "Postion = $position")
        holder.bind(resultFeetStepList[position])
    }

    override fun getItemCount() = resultFeetStepList.size

}