package com.softsquared.template.kotlin.src.main.feed.info

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.databinding.ItemGalleryinfoCommentBinding
import com.softsquared.template.kotlin.src.main.gallery.info.GalleryInfoActivity
import com.softsquared.template.kotlin.src.main.gallery.info.GalleryInfoFragment
import com.softsquared.template.kotlin.src.main.gallery.info.GalleryInfoFragmentAdapter
import com.softsquared.template.kotlin.src.main.gallery.info.GalleryInfoFragmentService
import com.softsquared.template.kotlin.src.main.gallery.info.models.CommentList

class FeedInfoActivityAdapter(
    private val feedInfoActivity: FeedInfoActivity,
    private val commentList: ArrayList<CommentList>
) : RecyclerView.Adapter<FeedInfoActivityAdapter.MyViewHolder>() {

    class MyViewHolder(
        private val feedInfoActivity: FeedInfoActivity,
        private val binding: ItemGalleryinfoCommentBinding,
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(commentList: CommentList) {
            with(binding) {
                galleryinfoTvCommentUsername.text = commentList.nickName
                galleryinfoTvCommentDes.text = commentList.content
            }


            // 댓글 Item 클릭 이벤트
            binding.root.setOnClickListener{
                Log.d("리사이클러 Item 체크", "data = "+ commentList)
            }


            binding.galleryinfoBtnCommentOption.setOnClickListener {

                /*
                To Do 1. 팝업메뉴 구현
                */
                // Activity 특성을 인자로 받기에, GallertInfoActivity 객체를 받아옴.
                val popup = PopupMenu(binding.root.context, binding.galleryinfoBtnCommentOption)
                feedInfoActivity.menuInflater.inflate(R.menu.menu_galleryinfo_comment_option, popup.menu)

                popup.setOnMenuItemClickListener {
                        item ->
                    when(item.itemId){


                        /*
                            To Do 2.  댓글 삭제 이벤트 구현
                         */
                        R.id.btn_del -> {

                            /*
                                To Do 2.1 삭제 다이얼로그 구현
                             */
                            val builder = AlertDialog.Builder(binding.root.context)
                            builder.setTitle("댓글 삭제하기")
                                .setMessage("해당 댓글을 삭제합니다.")
                                .setPositiveButton("확인",
                                    DialogInterface.OnClickListener { dialog, id ->
//                                        feedInfoActivity.showCustomToast("댓글 삭제완료")

                                        /*
                                            To Do 2.2 댓글 삭제 API 구현
                                         */
                                        FeedInfoService(feedInfoActivity).deletePostComment(commentList.commentId)
                                    })

                                .setNegativeButton("취소",
                                    DialogInterface.OnClickListener { dialog, id ->
//                                        feedInfoActivity.showCustomToast("댓글 삭제취소")
                                    })
                            // 다이얼로그를 띄워주기
                            builder.show()
                        }


                        /*
                            To Do 3. 댓글 신고 이벤트 구현
                        */
                        R.id.btn_flag -> {
                                /*
                                    To Do 3.2 신고하기 다이얼로그 구현
                                */
                            val builder = AlertDialog.Builder(binding.root.context)
                            builder.setTitle("댓글 신고하기")
                                .setMessage("해당 댓글을 신고합니다.")
                                .setPositiveButton("확인",
                                    DialogInterface.OnClickListener { dialog, id ->
                                        feedInfoActivity.showCustomToast("신고 접수완료")
                                    })

                                .setNegativeButton("취소",
                                    DialogInterface.OnClickListener { dialog, id ->
//                                        feedInfoActivity.showCustomToast("신고하기 접수 취소")
                                    })
                            // 다이얼로그를 띄워주기
                            builder.show()
                        }


                    }
                    false
                }

                // 팝업 메뉴 아이콘 표시
                try {
                    val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                    fieldMPopup.isAccessible = true
                    val mPopup = fieldMPopup.get(popup)
                    mPopup.javaClass
                        .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                        .invoke(mPopup, true)
                } catch (e: Exception){
                    Log.e("Main", "Error showing menu icons")
                } finally {
                    popup.show()
                }

            }
        }

    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(feedInfoActivity,
            ItemGalleryinfoCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d("포지션 값 체크", "Postion = $position")
        holder.bind(commentList[position])
    }

    override fun getItemCount() = commentList.size

}
