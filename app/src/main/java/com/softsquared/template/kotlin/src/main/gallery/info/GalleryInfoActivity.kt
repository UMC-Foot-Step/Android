package com.softsquared.template.kotlin.src.main.gallery.info

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.databinding.ActivityGalleryinfoBinding
import com.softsquared.template.kotlin.src.main.gallery.info.models.PostInfoResponse
import com.softsquared.template.kotlin.src.main.gallery.info.models_sample.FeetStepInfoResponse


class GalleryInfoActivity()
    : BaseActivity<ActivityGalleryinfoBinding>(ActivityGalleryinfoBinding::inflate),
    GalleryInfoActivityInterface{

    private var posting_id: Int = 0


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
        posting_id = intent.getIntExtra("posting-id",0)


        // 게시글 상세 조회 API 요청 값 서비스에 전달
        // 요청객체 = Path variable
//        val postInfoRequest =  PostInfoRequest(posting_id = posting_id )
         GalleryInfoService(this).getPostInfo(posting_id)


        // 뒤로가기 버튼
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
            menuInflater.inflate(R.menu.menu_galleryinfo_option_btn, popup.menu)


            popup.setOnMenuItemClickListener {
                item ->
                when(item.itemId){

                    // 게시글 신고하기 메뉴
//                    R.id.btn_flag -> {
//                        showCustomToast("신고하기 버튼 클릭")
//                    }

                    // 게시글 수정하기 메뉴
                    R.id.btn_edit -> {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("게시글 수정하기")
                            .setMessage("해당 게시글을 수정합니다.")
                            .setPositiveButton("확인",
                                DialogInterface.OnClickListener { dialog, id ->
                                    showCustomToast("게시글 수정 진행")

                                    /*
                                        To Do 3.3 게시글 수정 액티비티로 전환
                                     */

                                    finish()
                                })
                            .setNegativeButton("취소",
                                DialogInterface.OnClickListener { dialog, id ->
                                    showCustomToast("게시글 수정 취소")
                                })
                        // 다이얼로그를 띄워주기
                        builder.show()

                        showCustomToast("수정하기 버튼 클릭")
                        // 수정하기 재확인 다이얼로그 띄움
                        // 수정하기 activity로 전환
                    }

                    // 게시글 삭제하기 메뉴
                    R.id.btn_del -> {
                    //기본 형태의 다이얼로그 - 1/26 ~ 1/31 해결예정

                    // 다이얼로그를 생성하기 위해 Builder 클래스 생성자를 이용해 줍니다.
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("게시글 삭제하기")
                            .setMessage("해당 게시글을 삭제합니다.")
                            .setPositiveButton("확인",
                                DialogInterface.OnClickListener { dialog, id ->
                                    showCustomToast("게시글 삭제완료")

                                    /*
                                        To Do 3.2 게시글 삭제 API 구현
                                     */
                                    GalleryInfoService(this).deletePost(posting_id)

                                })
                            .setNegativeButton("취소",
                                DialogInterface.OnClickListener { dialog, id ->
                                    showCustomToast("게시글 삭제취소")
                                })
                        // 다이얼로그를 띄워주기
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




    }



    /*
        정보 상세 조회 API 메소드
     */
    // 게시글 Idx로 특정 발자취 게시글 정보 상세 조회 API 요청 값 받아서,
    // Activity View 구현
    // 정보 상세  조회 API 요청 & 응답 성공
    override fun onGetPostInfoSuccess(response: PostInfoResponse) {
        binding.galleryTvTitle.text = response.result.postingName

        // Fragment에서 뿌릴 데이터 전달
        supportFragmentManager.beginTransaction().replace(com.softsquared.template.kotlin.R.id.galleryinfo_frm, GalleryInfoFragment(this, response.result, posting_id,)).commitAllowingStateLoss()

    }
    // 정보 상세 조회 API 요청 실패
    override fun onGetPostInfoFailure(message: String) {
        showCustomToast("API 요청 실패, LogCat 확인")
        Log.d("왜 실패 했니?", message)
    }



    /*
        게시글 삭제 API 메소드
     */
    override fun onDeletePostSuccess(response: BaseResponse) {
        finish()
    }
    override fun onDeletePostFailure(message: String) {
        showCustomToast("API 요청 실패, LogCat 확인")
        Log.d("왜 실패 했니?", message)
    }










}