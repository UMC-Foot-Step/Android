package com.softsquared.template.kotlin.src.main.feed

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.ItemFeedPostBinding
import com.softsquared.template.kotlin.src.main.feed.models.FeedList

class FeedListFragmentAdapter(
    private val feedList: ArrayList<FeedList>
) : RecyclerView.Adapter<FeedListFragmentAdapter.MyViewHolder>() {

    class MyViewHolder(
        private val binding: ItemFeedPostBinding
    ) : RecyclerView.ViewHolder(binding.root){


        fun bind(feedList: FeedList) {
            with(binding) {
                /*
                    To Do 1. 리사이클러 뷰 업데이트
                */
                Glide.with(binding.root.context).load(feedList.imageUrl).into(feedlistIvFeedImg)
                feedlistTvUserNickname.text = feedList.nickName
                feedlistTvFeedTitle.text = feedList.title
                feedlistTvPosition.text = feedList.placeName
                feedlistTvLikeCnt.text = feedList.likes.toString()
                feedlistTvCommentCnt.text = feedList.commentCount.toString()
                feedlistTvUploadDate.text = feedList.recordDate.substring(0 until 4) + ". ".toString() + feedList.recordDate.substring(5 until 7) + ". " + feedList.recordDate.substring(8 until 10)


            }


            /*
                To Do 2. 유저 닉네임 클릭 이벤트
             */
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedListFragmentAdapter.MyViewHolder {
        return FeedListFragmentAdapter.MyViewHolder(
            ItemFeedPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FeedListFragmentAdapter.MyViewHolder, position: Int) {
        Log.d("포지션 값 체크", "Postion = $position")
        holder.bind(feedList[position])
    }

    override fun getItemCount() = feedList.size
}