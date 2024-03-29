package com.footstep.dangbal.kotlin.src.main.feed.info

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.footstep.dangbal.kotlin.R
import com.footstep.dangbal.kotlin.config.BaseActivity
import com.footstep.dangbal.kotlin.config.BaseResponse
import com.footstep.dangbal.kotlin.databinding.ActivityFeedinfoBinding
import com.footstep.dangbal.kotlin.src.main.feed.models.ReportResponse
import com.footstep.dangbal.kotlin.src.main.feed.models.createReportDto
import com.footstep.dangbal.kotlin.src.main.gallery.info.models.PostCommentRequest
import com.footstep.dangbal.kotlin.src.main.gallery.info.models.PostInfoResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedInfoActivity()
    : BaseActivity<ActivityFeedinfoBinding>(ActivityFeedinfoBinding::inflate),
    FeedInfoActivityInterface{

    private var posting_id: Int = 0
    private var userId: Int = 0

    private lateinit var feedInfoActivity: FeedInfoActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        feedInfoActivity = this
        /*
        To  Do 1.
        FeedListFragment 에서 전달받은 게시글 Idx 받기 & 발자취 게시글 상세조회 API 엮기
        */
        // 상세 조회 게시글 idx 받기
        val intent = intent
        posting_id = intent.getIntExtra("posting-id",0)
        userId = intent.getIntExtra("userId",0)



        // 발자취 게시글 상세 조회 API 호출
        FeedInfoService(this).getPostInfo(posting_id)



        /*
            To Do 2. 뒤로가기 버튼
         */
        binding.galleryinfoBtnBack.setOnClickListener {
            finish()
        }


        /*
            To Do 3. 옵션 팝업메뉴
         */
        binding.galleryinfoBtnOption.setOnClickListener {

            /*
                To Do 3.1 팝업메뉴 구현
             */
            var popup = PopupMenu(this, it)
            menuInflater.inflate(R.menu.menu_feedinfo_option_btn, popup.menu)


            popup.setOnMenuItemClickListener {
                    item ->
                when(item.itemId){

                    // 게시글 신고하기 메뉴
                    R.id.btn_flag -> {

                        /*
                            To Do 3.2 신고하기 다이얼로그 구현
                        */
                        /*
                        val builder = AlertDialog.Builder(binding.root.context)
                        builder.setTitle("게시글 신고하기")
                            .setMessage("해당 게시글을 신고합니다.")
                            .setPositiveButton("확인",
                                DialogInterface.OnClickListener { dialog, id ->
                                    showCustomToast("신고 접수완료")
                                })

                            .setNegativeButton("취소",
                                DialogInterface.OnClickListener { dialog, id ->
//                                    showCustomToast("신고하기 접수 취소")
                                })
                        // 다이얼로그를 띄워주기
                        builder.show()
                        */
                        val reportItems = arrayOf("게시글", "유저(유저가 쓴 모든 콘텐츠가 삭제됩니다.)")
                        var selectedItem: String? = null
                        var selectedNum: Int = 0
                        val builder = AlertDialog.Builder(this)
                            .setTitle("신고대상")
                            .setSingleChoiceItems(reportItems, -1) { dialog, which ->
                                selectedNum = which
                                selectedItem = reportItems[which]
                            }
                            .setPositiveButton("확인") { dialog, which ->
                                if(selectedNum==0){
                                    //showCustomToast("게시글 신고하기")
                                    // bottomSheetDialog - 신고사유
                                    reportDialogPosting(posting_id)
                                    // api 연결 성공
                                }
                                else{
                                    //showCustomToast("유저 신고하기")
                                    // bottomSheetDialog - 신고사유
                                    reportDialogUser()
                                }
                            }
                            .setNegativeButton("취소",
                            DialogInterface.OnClickListener { dialog, id ->
                                showCustomToast("신고하기 취소")
                            })
                        builder.show()
                    }
                }
                false
            }

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




        /*
            To Do 4. 좋아요 버튼 이벤트
        */
        binding.galleryBtnLike.setOnClickListener {
            FeedInfoService(this).postPostLike(posting_id)

            if(binding.galleryBtnLike.background == ContextCompat.getDrawable(baseContext, R.drawable.ic_postlike_btn_unselected))
                binding.galleryBtnLike.background=
                    ContextCompat.getDrawable(baseContext, R.drawable.ic_postlike_btn_selected)

            else if(binding.galleryBtnLike.background == ContextCompat.getDrawable(baseContext, R.drawable.ic_postlike_btn_selected))
                binding.galleryBtnLike.background=
                    ContextCompat.getDrawable(baseContext, R.drawable.ic_postlike_btn_unselected)

        }


        /*
            To Do 5. 댓글 작성
        */
        binding.galleryinfoBtnComment.setOnClickListener {

            /*
                To Do 4.1 Edit Text 댓글
                댓글 작성 API 요청객체로 생성
             */
            val postRequest = PostCommentRequest(content = binding.galleryinfoEtComment.text.toString())

            /*
                To Do 4.2 댓글 작성 API 호출
             */
            FeedInfoService(this).postPostComment(postRequest, posting_id)

            // 값 초기화
            binding.galleryinfoEtComment.setText(null)
            // 키보드 내리기
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.galleryinfoEtComment.windowToken, 0)

        }


    }



    // 댓글 작성 중에 화면 다른 요소 클릭 시 키보드 내리기 메소드
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }





    /*
        To Do 1. 게시글 상세 조회 API 요청 성공 메소드
    */
    override fun onGetPostInfoSuccess(response: PostInfoResponse) {
        binding.galleryTvTitle.text = response.result.postingName
        Glide.with(binding.root.context).load(response.result.imageUrl).into(binding.galleryinfoIvPostImg)
        binding.galleryinfoTvDay.text = response.result.postingDate.substring(0 until 4) + ". ".toString() + response.result.postingDate.substring(5 until 7) + ". " + response.result.postingDate.substring(8 until 10)
        binding.galleryinfoPostPosition.text = response.result.placeName
        binding.galleryinfoTvLikeCnt.text = response.result.likeNum.toString()
        binding.galleryinfoTvCommentCnt.text = response.result.commentNum.toString()
        binding.galleryinfoTvPostUsername.text = response.result.nickName
        binding.galleryinfoTvPostDes.text = response.result.content

        if(response.result.isLike==1)
            binding.galleryBtnLike.setBackgroundResource(R.drawable.ic_postlike_btn_selected)
        else if(response.result.isLike==0)
            binding.galleryBtnLike.setBackgroundResource(R.drawable.ic_postlike_btn_unselected)
        /*
            To Do 2. 리사이클러 뷰에 데이터 뿌리기
        */
        binding.galleryinfoRvComment.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(binding.root.context)
            adapter = FeedInfoActivityAdapter(feedInfoActivity, response.result.commentList)

            // RecyclerView Item 구분선 넣기
            addItemDecoration(
                DividerItemDecoration(
                    binding.root.context,
                    DividerItemDecoration.VERTICAL
                )
            )


        }



    }
    // 상세 조회 API 요청 실패 메소드
    override fun onGetPostInfoFailure(message: String) {
        Log.d("API 요청 실패", message)
        showCustomToast(message)
    }


    // 댓글 삭제 API 요청성공 메소드
    override fun onDeletePostCommentSuccess(response: BaseResponse) {
        /*
            To Do 1. 상세 조회 뷰 업데이트
         */
        if(response.isSuccess == false) {
            Log.d("reportProcess", "피드 댓글 삭제 onDeletePostCommentSuccess false")

            val builder = AlertDialog.Builder(binding.root.context)
            builder.setTitle("댓글 삭제하기")
                .setMessage("타 유저의 댓글을 삭제하실 수가 없습니다.")
                .setPositiveButton("확인",
                    DialogInterface.OnClickListener { dialog, id ->
                    })
            // 다이얼로그를 띄워주기
            builder.show()
        }
        else {
            Log.d("reportProcess", "피드 댓글 삭제 onDeletePostCommentSuccess true")

            FeedInfoService(this).getPostInfo(posting_id)
        }

    }
    // 댓글 삭제 API 요청실패 메소드
    override fun onDeletePostCommentFailure(message: String) {
        Log.d("API 요청 실패", message)
        showCustomToast(message)
    }


    // 댓글 & 좋아요 클릭 API 요청실패 메소드
    override fun onDeletePostFailure(message: String) {
        Log.d("API 요청 실패", message)
        showCustomToast(message)
    }

    // 신고 사유 bottomSheetDialog
    private fun reportDialogPosting(posting_id:Int) {
        // val dialog = BottomSheetDialog(this)
        // dialog.setContentView(R.layout.dialog_report)

        val bottomSheet = layoutInflater.inflate(R.layout.dialog_report, null)
        // 스타일 둥글게 적용
        val bottomSheetDialog = BottomSheetDialog(this, R.style.calTheme_Custom)
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
            var reasonNum=0
            when(reportGroup.checkedRadioButtonId){
                R.id.btn_report_0 -> {
                    reasonNum=0
                }
                R.id.btn_report_1 -> {
                    reasonNum=1
                }
                R.id.btn_report_2 -> {
                    reasonNum=2
                }
                R.id.btn_report_3 -> {
                    reasonNum=3
                }
                R.id.btn_report_4 -> {
                    reasonNum=4
                }
                R.id.btn_report_5 -> {
                    reasonNum=5
                }
                R.id.btn_report_6 -> {
                    reasonNum=6
                }

            }
            showCustomToast("신고하기 처리중입니다")
            FeedInfoService(feedInfoActivity).ReportPost(
                createReportDto(reasonNumber = reasonNum, targetNumber = 1),posting_id,
            )
            // bottomSheetDialog 닫기
            bottomSheetDialog.dismiss()
            // 게시글 신고 완료 alertDialog


        }
    }
    // 유저
    private fun reportDialogUser() {
        // val dialog = BottomSheetDialog(this)
        // dialog.setContentView(R.layout.dialog_report)
        Log.d("신고하기","피드 게시글 유저 ${userId.toString()}")

        val bottomSheet = layoutInflater.inflate(R.layout.dialog_report, null)
        // 스타일 둥글게 적용
        val bottomSheetDialog = BottomSheetDialog(this, R.style.calTheme_Custom)
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
            var reasonNum=0
            when(reportGroup.checkedRadioButtonId){
                R.id.btn_report_0 -> {
                    reasonNum=0
                }
                R.id.btn_report_1 -> {
                    reasonNum=1
                }
                R.id.btn_report_2 -> {
                    reasonNum=2
                }
                R.id.btn_report_3 -> {
                    reasonNum=3
                }
                R.id.btn_report_4 -> {
                    reasonNum=4
                }
                R.id.btn_report_5 -> {
                    reasonNum=5
                }
                R.id.btn_report_6 -> {
                    reasonNum=6
                }

            }
            showCustomToast("신고하기 처리중입니다")
            FeedInfoService(feedInfoActivity).ReportUser(
                userId,createReportDto(reasonNumber = reasonNum, targetNumber = 0)
            )
            // bottomSheetDialog 닫기
            bottomSheetDialog.dismiss()
            // 유저 신고 완료 alertDialog
           // reportSuccessUser()

        }
    }

    // 게시글 신고 완료
    private fun reportSuccessPosting() {
        val builder = AlertDialog.Builder(binding.root.context)
            .setMessage("게시글 신고가 완료되었습니다 \n(각기 다른 사용자에게 신고가 3번 누적될 경우 해당 계정은 한달간 정지됩니다.)")
            .setPositiveButton("확인", 
                DialogInterface.OnClickListener { dialog, id -> 
                    //showCustomToast("신고 접수완료")
                }
            )
        
        // 다이얼로그 띄우기
        builder.show()
    }

    // 유저 신고 완료
    private fun reportSuccessUser() {
        val builder = AlertDialog.Builder(binding.root.context)
            .setMessage("유저 신고가 완료되었습니다 \n(각기 다른 사용자에게 신고가 3번 누적될 경우 해당 계정은 한달간 정지됩니다.)")
            .setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, id ->
                    //showCustomToast("신고 접수완료")
                }
            )

        // 다이얼로그 띄우기
        builder.show()
    }

    // 댓글 신고 완료
    private fun reportSuccessComment() {
        val builder = AlertDialog.Builder(binding.root.context)
            .setMessage("댓글 신고가 완료되었습니다 \n(각기 다른 사용자에게 신고가 3번 누적될 경우 해당 계정은 한달간 정지됩니다.)")
            .setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, id ->
                    //feedInfoActivity.showCustomToast("신고 접수완료")
                }
            )

        // 다이얼로그 띄우기
        builder.show()
    }

    //댓글신고 api
    override fun onReportCommentSuccess(response: ReportResponse) {
        Log.d("reportProcess", "onReportCommentSuccess ${response.toString()}")
        CoroutineScope(Dispatchers.Main).launch {
            reportSuccessComment()
        }
    }

    override fun onReportCommentFailure(message: String) {
        showCustomToast("API 요청 실패, LogCat 확인")
        Log.d("reportProcess", message)
    }


    //유저신고 api
    override fun onReportUserSuccess(response: ReportResponse) {
        Log.d("reportProcess", "피드 유저신고 response.toString :  ${response.toString()}")

        CoroutineScope(Dispatchers.Main).launch {
            reportSuccessUser()
        }
    }

    override fun onReportUserFailure(message: String) {
        showCustomToast("API 요청 실패, LogCat 확인")
        Log.d("reportProcess", message)
    }


    //게시물신고 api
    override fun onReportPostSuccess(response: ReportResponse) {
        Log.d("reportProcess", "oonReportPostSuccess ${response.toString()}")

        CoroutineScope(Dispatchers.Main).launch {
            reportSuccessPosting()
        }
    }

    override fun onReportPostFailure(message: String) {
        showCustomToast("API 요청 실패, LogCat 확인")
        Log.d("reportProcess", "FeedInfo "+message)
    }
}