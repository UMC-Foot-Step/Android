package com.softsquared.template.kotlin.src.main.gallery.info

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityGalleryinfoBinding
import com.softsquared.template.kotlin.src.main.gallery.info.models.FeetStepInfoResponse
import com.softsquared.template.kotlin.src.main.map.MapFragment


class GalleryInfoActivity()
    : BaseActivity<ActivityGalleryinfoBinding>(ActivityGalleryinfoBinding::inflate),
    GalleryInfoActivityInterface{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // GalleryFragment 에서 전달 받은 데이터객체 받기
        val intent = intent
        val feetStepInfoResponse: FeetStepInfoResponse
        feetStepInfoResponse = intent.getSerializableExtra("feetStepInfoResponse") as FeetStepInfoResponse

        // 전달받은 데이터객체에서 게시글 Idx로 특정 발자취 게시글 조회
            // 프론트 리소스 상 API 요청은 생략
            // 로직만 비슷하게 설계해둠
        // GalleryInfoService(this).getPostInfo(postIdx)
        onGetPostInfoSuccess(feetStepInfoResponse)

        supportFragmentManager.beginTransaction().replace(com.softsquared.template.kotlin.R.id.galleryinfo_frm, GalleryInfoFragment(this, feetStepInfoResponse)).commitAllowingStateLoss()

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
    override fun onGetPostInfoSuccess(response: FeetStepInfoResponse) {
        binding.galleryTvTitle.text = response.title

        // Fragment에서 뿌릴 데이터 전달
    }

    override fun onGetPostInfoFailure(message: String) {
    }


    // Fragment로 API 응답 데이터 전달
    private fun setupFragemntLayout(response: FeetStepInfoResponse){
        supportFragmentManager.beginTransaction().replace(com.softsquared.template.kotlin.R.id.galleryinfo_frm, GalleryInfoFragment(this, response)).commitAllowingStateLoss()
    }


    private fun closeGalleryInfoActivity(){
        finish()
    }







}