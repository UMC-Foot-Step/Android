package com.softsquared.template.kotlin.src.main.gallery.info

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.databinding.FragmentGalleryinfoBinding
import com.softsquared.template.kotlin.src.main.feed.models.ReportResponse
import com.softsquared.template.kotlin.src.main.gallery.GalleryService
import com.softsquared.template.kotlin.src.main.gallery.info.models.PostCommentRequest
import com.softsquared.template.kotlin.src.main.gallery.info.models.PostInfoResponse
import com.softsquared.template.kotlin.src.main.gallery.info.models.ResultPostInfo
import com.softsquared.template.kotlin.src.main.gallery.info.models_sample.FeetStepInfoResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// GalleryInfoFragment 생성자 = 발자취 게시글 상세 정보 (게시글 정보 & 댓글 리스트)
class GalleryInfoFragment (
    val galleryInfoActivity: GalleryInfoActivity,
    val resultPostInfo: ResultPostInfo,
    val posting_id: Int
    ) :
    BaseFragment<FragmentGalleryinfoBinding>(FragmentGalleryinfoBinding::bind, R.layout.fragment_galleryinfo),
    GalleryInfoFragmentInterface{

    // GalleryInfoFragment 객체선언
    private lateinit var galleryInfoFragment: GalleryInfoFragment


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        galleryInfoFragment = this

        /*
            To Do. feetStepInfoResponse 객체 받아서, Fragment View에 뿌리기
         */

        // To DO 1. 발자취 게시글 상세 정보 뿌리기
        Glide.with(binding.root.context).load(resultPostInfo.imageUrl).into(binding.galleryinfoIvPostImg)
        binding.galleryinfoTvDay.text = resultPostInfo.postingDate.substring(0 until 4) + ". ".toString() + resultPostInfo.postingDate.substring(5 until 7) + ". " + resultPostInfo.postingDate.substring(8 until 10)
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
            adapter = GalleryInfoFragmentAdapter(galleryInfoActivity, galleryInfoFragment, resultPostInfo.commentList)

            // RecyclerView Item 구분선 넣기
                addItemDecoration(
                    DividerItemDecoration(binding.root.context,
                        DividerItemDecoration.VERTICAL)
                )
        }



        /*
            To Do 3. 좋아요 버튼 클릭
         */
        binding.galleryBtnLike.setOnClickListener {

            /*
                To Do 3.1 좋아요 클릭 API 호출
            */
            GalleryInfoService(galleryInfoActivity).postPostLike(posting_id)
        }


        /*
            To Do 4. 댓글 작성
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
            GalleryInfoService(galleryInfoActivity).postPostComment(postRequest, posting_id)
        }




    }

    /*
        발자취 게시글 댓글 삭제 API 메소드
     */
    override fun onDeletePostCommentSuccess(response: BaseResponse) {

        if(response.isSuccess == false) {
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
            /*
            To Do 1. 게시글 뷰 업데이트 - 정보 상세 조회 API 재호출
         */
            GalleryInfoService(galleryInfoActivity).getPostInfo(posting_id)
        }
    }
    override fun onDeletePostCommentFailure(message: String) {
        showCustomToast("API 요청 실패, LogCat 확인")
        Log.d("왜 실패 했니?", message)
    }

    // 댓글 신고 완료
    private fun reportSuccessComment() {
        val builder = AlertDialog.Builder(galleryInfoActivity)
            .setMessage("댓글 신고가 완료되었습니다 \n(각기 다른 사용자에게 신고가 3번 누적될 경우 해당 계정은 한달간 정지됩니다.)")
            .setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, id ->
                    //galleryInfoFragment.showCustomToast("신고 접수완료")
                    showCustomToast("신고 접수완료")
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
                    //galleryInfoFragment.showCustomToast("신고 접수완료")
                    showCustomToast("신고 접수완료")
                }
            )

        // 다이얼로그 띄우기
        builder.show()
    }

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

    override fun onReportUserSuccess(response: ReportResponse) {
        Log.d("reportProcess", "onReportUserSuccess ${response.toString()}")
        CoroutineScope(Dispatchers.Main).launch {
            reportSuccessUser()
        }
    }

    override fun onReportUserFailure(message: String) {
        showCustomToast("API 요청 실패, LogCat 확인")
        Log.d("reportProcess", message)
    }
}
