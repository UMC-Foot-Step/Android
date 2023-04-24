package com.footstep.dangbal.kotlin.src.main.feed.specific

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.footstep.dangbal.kotlin.databinding.ItemGallerySubSectionBinding
import com.footstep.dangbal.kotlin.src.main.gallery.models.PostList

class FeedSpecificListFragmentSubAdapter(
    private val resultFeetStepList: ArrayList<PostList>,
    val feedSpecificListFragmentInterface: FeedSpecificListFragmentInterface,
    private val userId:Int
) : RecyclerView.Adapter<FeedSpecificListFragmentSubAdapter.MyViewHolder>() {
    class MyViewHolder(
        private val binding: ItemGallerySubSectionBinding,
        private val feedSpecificListFragmentInterface: FeedSpecificListFragmentInterface,
        private val userId:Int
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(resultFeetStep: PostList) {
            with(binding) {
                Glide.with(binding.root.context).load(resultFeetStep.imageUrl).into(galleryIvPostimg)
                galleryTvPostTitle.text = resultFeetStep.title
                galleryTvPostlikeCnt.text = resultFeetStep.likes_cnt.toString()
                galleryTvPostPosition.text = resultFeetStep.placeName
            }

            binding.root.setOnClickListener{
                val pos: Int = getAdapterPosition()
                Log.d("리사이클러 뷰 포지션 값 체크", "position = "+pos)
                Log.d("리사이클러 Item 체크", "data = "+ resultFeetStep)

                // 게시글 인덱스
                val post_idx: Int = resultFeetStep.postingId
                val userId:Int=userId
                feedSpecificListFragmentInterface.changeFeedInfoActivity(post_idx,userId)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemGallerySubSectionBinding.inflate(
                LayoutInflater.from(parent.context),
            parent, false), feedSpecificListFragmentInterface,userId)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d("포지션 값 체크", "Postion = $position")
        holder.bind(resultFeetStepList[position])
    }

    override fun getItemCount() = resultFeetStepList.size

}