package com.softsquared.template.kotlin.src.main.gallery.info

import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityGalleryinfoBinding
import com.softsquared.template.kotlin.src.main.gallery.info.models.PostInfoResponse
import com.softsquared.template.kotlin.src.main.gallery.info.models_sample.FeetStepInfoResponse


class GalleryInfoActivity()
    : BaseActivity<ActivityGalleryinfoBinding>(ActivityGalleryinfoBinding::inflate),
    GalleryInfoActivityInterface{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /*
            To Do  1.
            GalleryFragment 에서 전달 받은 데이터객체 받기
         */
//        val intent = intent
//        val feetStepInfoResponse: FeetStepInfoResponse
//        feetStepInfoResponse = intent.getSerializableExtra("feetStepInfoResponse") as FeetStepInfoResponse
//        onGetPostInfoSuccess(feetStepInfoResponse)
        // 전달받은 데이터객체에서 게시글 Idx로 특정 발자취 게시글 조회
        // 프론트 리소스 상 API 요청은 생략
        // 로직만 비슷하게 설계해둠

        /*
            To  Do 2.
            GalleryFragment 에서 전달받은 게시글 Idx 받기 & 발자취 게시글 상세조회 API 엮기
         */

        // 상세 조회 게시글 idx 받기
        val intent = intent
        val posting_id: Int
        posting_id = intent.getIntExtra("posting-id",0)


        // 게시글 상세 조회 API 요청 값 서비스에 전달
        // 요청객체 = Path variable
//        val postInfoRequest =  PostInfoRequest(posting_id = posting_id )
         GalleryInfoService(this).getPostInfo(posting_id)


//        supportFragmentManager.beginTransaction().replace(com.softsquared.template.kotlin.R.id.galleryinfo_frm, GalleryInfoFragment(this, feetStepInfoResponse)).commitAllowingStateLoss()

        // 뒤로가기 버튼
        binding.galleryinfoBtnBack.setOnClickListener {
            finish()
        }

        // 옵션 팝업메뉴
        binding.galleryinfoBtnOption.setOnClickListener {

            /*
                To Do 1. 팝업메뉴 구현
             */
            var popup = PopupMenu(this, binding.galleryinfoBtnOption)
            menuInflater.inflate(R.menu.menu_galleryinfo_option_btn, popup.menu)


            popup.setOnMenuItemClickListener {
                item ->
                when(item.itemId){
                    R.id.btn_flag ->
                        showCustomToast("신고하기 버튼 클릭")
                    R.id.btn_edit ->
                        showCustomToast("수정하기 버튼 클릭")
                        // 수정하기 재확인 다이얼로그 띄움
                        // 수정하기 activity로 전환
                    R.id.btn_del ->
                        showCustomToast("삭제하기 버튼 클릭")
                        // 삭제하기 재확인 다이얼로그 띄움
                        // 게시글 삭제 API 호출 & 발자취 게시글 상세 조회 Activity 종료
                        // & 갤러리 Fragment 업데이트 메소드 호출

                      // 기본 형태의 다이얼로그 - 1/26 ~ 1/31 해결예정
//                    alertBtn.setOnClickListener {
//                        // 다이얼로그를 생성하기 위해 Builder 클래스 생성자를 이용해 줍니다.
//                        val builder = AlertDialog.Builder(this)
//                        builder.setTitle("타이틀 입니다.")
//                            .setMessage("메세지 내용 부분 입니다.")
//                            .setPositiveButton("확인",
//                                DialogInterface.OnClickListener { dialog, id ->
//                                    resultText.text = "확인 클릭"
//                                })
//                            .setNegativeButton("취소",
//                                DialogInterface.OnClickListener { dialog, id ->
//                                    resultText.text = "취소 클릭"
//                                })
//                        // 다이얼로그를 띄워주기
//                        builder.show()
//                    }


                }
                    false
            }
            popup.show()
        }




    }


    // 게시글 Idx로 특정 발자취 게시글 정보 상세 조회 API 요청 값 받아서,
    // Activity View 구현
    // 정보 상세  조회 API 요청 & 응답 성공
    override fun onGetPostInfoSuccess(response: PostInfoResponse) {
        binding.galleryTvTitle.text = response.result.placeName

        // Fragment에서 뿌릴 데이터 전달
        supportFragmentManager.beginTransaction().replace(com.softsquared.template.kotlin.R.id.galleryinfo_frm, GalleryInfoFragment(this, response.result)).commitAllowingStateLoss()

    }

    // 정보 상세 조회 API 요청 실패
    override fun onGetPostInfoFailure(message: String) {
        showCustomToast(message)
        Log.d("왜 실패 했니?", message)
    }


    private fun closeGalleryInfoActivity(){
        finish()
    }







}