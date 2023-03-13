package com.softsquared.template.kotlin.src.main.feed.info

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
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
                            /*
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
                            */
                            val reportItems = arrayOf("댓글", "유저(유저가 쓴 모든 콘텐츠가 삭제됩니다.)")
                            var selectedItem: String? = null
                            var selectedNum: Int = 0
                            val builder = AlertDialog.Builder(binding.root.context)
                                .setTitle("신고대상")
                                .setSingleChoiceItems(reportItems, -1) { dialog, which ->
                                    selectedNum = which
                                    selectedItem = reportItems[which]
                                }
                                .setPositiveButton("확인") { dialog, which ->
                                    // 댓글을 누르고 확인 버튼
                                    if(selectedNum==0){
                                        feedInfoActivity.showCustomToast("댓글 신고하기")
                                        // bottomSheetDialog - 신고사유
                                        reportDialogComment()

                                    }
                                    // 유저를 누르고 확인 버튼
                                    else{
                                        feedInfoActivity.showCustomToast("유저 신고하기")
                                        // bottomSheetDialog - 신고사유
                                        reportDialogUser()
                                    }
                                }
                                .setNegativeButton("취소",
                                DialogInterface.OnClickListener { dialog, id ->
                                    feedInfoActivity.showCustomToast("신고하기 취소")
                                })
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

        // 신고하기 이유 BottomSheetDialog
        // 댓글
        private fun reportDialogComment() {
            // val dialog = BottomSheetDialog(this)
            // dialog.setContentView(R.layout.dialog_report)

            val bottomSheet = feedInfoActivity.layoutInflater.inflate(R.layout.dialog_report, null)
            // 스타일 둥글게 적용
            val bottomSheetDialog = BottomSheetDialog(feedInfoActivity, R.style.calTheme_Custom)
            // 닫기
            val btnClose = bottomSheet.findViewById<ImageButton>(R.id.btn_report_close)
            // 확인
            val btnCheck = bottomSheet.findViewById<Button>(R.id.reportBtnCheck)
            // 신고사유
            val reportGroup = bottomSheet.findViewById<RadioGroup>(R.id.report_group)

            bottomSheetDialog.setContentView(bottomSheet)
            bottomSheetDialog.show()

            // 닫기
            btnClose.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            // 확인
            btnCheck.setOnClickListener {
                when(reportGroup.checkedRadioButtonId){
                    R.id.btn_report_0 -> feedInfoActivity.showCustomToast("0번 신고사유")
                    R.id.btn_report_1 -> feedInfoActivity.showCustomToast("1번 신고사유")
                    R.id.btn_report_2 -> feedInfoActivity.showCustomToast("2번 신고사유")
                    R.id.btn_report_3 -> feedInfoActivity.showCustomToast("3번 신고사유")
                    R.id.btn_report_4 -> feedInfoActivity.showCustomToast("4번 신고사유")
                    R.id.btn_report_5 -> feedInfoActivity.showCustomToast("5번 신고사유")
                    R.id.btn_report_6 -> feedInfoActivity.showCustomToast("6번 신고사유")

                }
                // bottomSheetDialog 닫기
                bottomSheetDialog.dismiss()
                // 댓글 신고 완료
                reportSuccessComment()
            }
        }

        // 유저
        private fun reportDialogUser() {
            // val dialog = BottomSheetDialog(this)
            // dialog.setContentView(R.layout.dialog_report)

            val bottomSheet = feedInfoActivity.layoutInflater.inflate(R.layout.dialog_report, null)
            // 스타일 둥글게 적용
            val bottomSheetDialog = BottomSheetDialog(feedInfoActivity, R.style.calTheme_Custom)
            // 닫기
            val btnClose = bottomSheet.findViewById<ImageButton>(R.id.btn_report_close)
            // 확인
            val btnCheck = bottomSheet.findViewById<Button>(R.id.reportBtnCheck)
            // 신고사유
            val reportGroup = bottomSheet.findViewById<RadioGroup>(R.id.report_group)

            bottomSheetDialog.setContentView(bottomSheet)
            bottomSheetDialog.show()

            // 닫기
            btnClose.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            // 확인
            btnCheck.setOnClickListener {
                when(reportGroup.checkedRadioButtonId){
                    R.id.btn_report_0 -> feedInfoActivity.showCustomToast("0번 신고사유")
                    R.id.btn_report_1 -> feedInfoActivity.showCustomToast("1번 신고사유")
                    R.id.btn_report_2 -> feedInfoActivity.showCustomToast("2번 신고사유")
                    R.id.btn_report_3 -> feedInfoActivity.showCustomToast("3번 신고사유")
                    R.id.btn_report_4 -> feedInfoActivity.showCustomToast("4번 신고사유")
                    R.id.btn_report_5 -> feedInfoActivity.showCustomToast("5번 신고사유")
                    R.id.btn_report_6 -> feedInfoActivity.showCustomToast("6번 신고사유")

                }
                // bottomSheetDialog 닫기
                bottomSheetDialog.dismiss()
                // 댓글 신고 완료
                reportSuccessUser()
            }
        }


        // 댓글 신고 완료
        private fun reportSuccessComment() {
            val builder = AlertDialog.Builder(feedInfoActivity)
                .setMessage("댓글 신고가 완료되었습니다 \n(각기 다른 사용자에게 신고가 3번 누적될 경우 해당 계정은 한달간 정지됩니다.)")
                .setPositiveButton("확인",
                    DialogInterface.OnClickListener { dialog, id ->
                        feedInfoActivity.showCustomToast("신고 접수완료")
                    }
                )

            // 다이얼로그 띄우기
            builder.show()
        }

        // 유저 신고 완료
        private fun reportSuccessUser() {
            val builder = AlertDialog.Builder(feedInfoActivity)
                .setMessage("유저 신고가 완료되었습니다 \n(각기 다른 사용자에게 신고가 3번 누적될 경우 해당 계정은 한달간 정지됩니다.)")
                .setPositiveButton("확인",
                    DialogInterface.OnClickListener { dialog, id ->
                        feedInfoActivity.showCustomToast("신고 접수완료")
                    }
                )

            // 다이얼로그 띄우기
            builder.show()
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

    // 신고 사유 bottomSheetDialog
    // 댓글




}
