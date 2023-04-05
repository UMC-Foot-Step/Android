package com.softsquared.template.kotlin.src.main.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.databinding.ItemFeedPostBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.feed.models.FeedList
import com.softsquared.template.kotlin.src.main.feed.specific.FeedSpecificListFragment

class FeedListFragmentAdapter(
    private val feedList: ArrayList<FeedList>,
    private val feedListFragmentInterface: FeedListFragmentInterface,
    private val activity: FragmentActivity
) : RecyclerView.Adapter<FeedListFragmentAdapter.MyViewHolder>() {

    class MyViewHolder(
        private val binding: ItemFeedPostBinding,
        private val feedListFragmentInterface: FeedListFragmentInterface,
        private val activity: FragmentActivity
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
                To Do 2. 특정 피드 클릭 이벤트
             */
            binding.root.setOnClickListener {
                val post_idx: Int = feedList.postingId
                val user_Id:Int=feedList.userId
                feedListFragmentInterface.changeFeedInfoActivity(post_idx,user_Id)
            }


            /*
                To Do 3. 유저 닉네임 클릭 이벤트
             */
            binding.feedlistTvUserNickname.setOnClickListener {
                var fragment = FeedSpecificListFragment()
                var bundle = Bundle()
                bundle.putInt("userId", feedList.userId)
                bundle.putString("userName", feedList.nickName)
                fragment.arguments = bundle //fragment의 arguments에 데이터를 담은 bundle을 넘겨줌

                activity?.supportFragmentManager!!.beginTransaction()
                    .replace(R.id.main_frm, fragment)
                    .commit()
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemFeedPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ), feedListFragmentInterface, activity)
    }

    override fun onBindViewHolder(holder: FeedListFragmentAdapter.MyViewHolder, position: Int) {
        Log.d("포지션 값 체크", "Postion = $position")
        holder.bind(feedList[position])
    }

    override fun getItemCount() = feedList.size
}