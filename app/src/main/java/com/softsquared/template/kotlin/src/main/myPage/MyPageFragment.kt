package com.softsquared.template.kotlin.src.main.myPage

import android.app.Activity.*
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentMyPageBinding
import com.softsquared.template.kotlin.src.login.LoginProcessActivity
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.changeNicknameInfo
import com.softsquared.template.kotlin.src.main.myPage.mypageResultFile.Resultmypage
import com.softsquared.template.kotlin.util.getJwt
import com.softsquared.template.kotlin.util.removeJwt


// 메인 - 마이페이지
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page){

    //호출 시점은 accessToken호출 때
    private val accessToken : String by lazy{
        val jwt = getJwt()

        if(jwt == null){
            startActivity(Intent(requireContext(),LoginProcessActivity::class.java))
            requireActivity().finish()

            //flag clear -> 로그인 화면 넘어가면 이전 activity삭제
            return@lazy ""
        }

        jwt
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getmypage()

        //닉네임 초기설정 = 마이페이지 누를때 서버에서 api로 가져옴.
        //갤러리 부분 수정할때 Glide가 많이 바뀌어서 그냥 하기보다는 물어보라고 하심.

        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //수정버튼
        binding.btnModify.setOnClickListener {

            binding.txtNickname.visibility = View.INVISIBLE

            binding.edtNickname.visibility = View.VISIBLE

            binding.btnModify.visibility = View.INVISIBLE

            binding.btnModifyOk.visibility = View.VISIBLE

        }



        binding.edtNickname.addTextChangedListener (object : TextWatcher{

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                if(editable.isNotEmpty()){
                    binding.btnModifyOk.isClickable =true
                    binding.btnModifyOk.setBackgroundColor(Color.TRANSPARENT)
                    binding.btnModifyOk.visibility = View.VISIBLE
                }else
                {
                    binding.btnModifyOk.isClickable = false
                    binding.btnModifyOk.visibility = View.INVISIBLE
                    showCustomToast("공백 불가")
                }
            }

        })

        //확인버튼
        binding.btnModifyOk.setOnClickListener {
            with(binding.edtNickname){
                if(isFocused){
                    clearFocus()

                }
            }


            binding.txtNickname.visibility = View.VISIBLE

            binding.edtNickname.visibility = View.INVISIBLE

            binding.btnModifyOk.visibility = View.INVISIBLE

            binding.btnModify.visibility = View.VISIBLE

            val aftername = binding.edtNickname.text.toString()

            if(binding.edtNickname.text.toString() == ""){
                binding.txtNickname.text = "닉네임"
            }else{
                binding.txtNickname.text = aftername
            }
            nicknamechange()

            imm.hideSoftInputFromWindow(binding.edtNickname.windowToken, 0)


        }
        //프로필 설정 버튼
        binding.imgBtnProfilePlus.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityResult.launch(intent)
        }

//        binding.btnWithdraw.setOnClickListener {
//            val word = binding.txtNickname.text.toString()
//            showLoadingDialog(requireContext())
//            MypageService(this).tryGetUserNickname(word)
//
//        }

        binding.btnLogout.setOnClickListener {

            val mDialogView = LayoutInflater.from(context).inflate(R.layout.fragment_dialog,null)

            val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)

            val mAlertDialog = mBuilder.show()

            val okButton = mDialogView.findViewById<Button>(R.id.btn_ok_log)
            okButton.setOnClickListener {
                showCustomToast("로그아웃 완료!")

                logout()

                //flag

                startActivity(Intent(activity,LoginProcessActivity::class.java))
            }
            val noButton = mDialogView.findViewById<Button>(R.id.btn_no_log)
            noButton.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }

        binding.btnWithdraw.setOnClickListener {

            val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_withdraw,null)

            val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)

            val mAlertDialog = mBuilder.show()

            val okButton = mDialogView.findViewById<Button>(R.id.btn_ok_with)
            okButton.setOnClickListener {
                showCustomToast("탈퇴 완료")

                withdraw()

                startActivity(Intent(activity,LoginProcessActivity::class.java))

            }
            val noButton = mDialogView.findViewById<Button>(R.id.btn_no_with)
            noButton.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }



    }//onCreate



    private fun getmypage(){


//        binding.txtNickname.text = mypageinformation.nickname
//        binding.txtFootprintNum.text = mypageinformation.postingcount.toString()
        MyPageService().tryGetMyPage(accessToken, object : MyPageView{

            override fun onMyPageSuccess(code: Int, result: Resultmypage) {

                when(code){
                    200->{
                        binding.txtNickname.text = result.nickname
                        binding.txtFootprintNum.text = result.postingCount.toString()
                        //갤러리는 찾는중
                    }
                }
            }

            override fun onMyPageFailure(message: String) {

            }

        } )


    }

    private fun nicknamechange(){

        NicknameService().trychangeNickname(accessToken , getNickname() , object : NicknameView{
            override fun onNicknameSuccess(code: Int, result: String) {
                when(code){
                    200->{
                        changeNicknameInfo(getNickname().toString())
                        showCustomToast(result)
                    }
                }
            }

            override fun onNicknameFailure(message: String) {
                showCustomToast(message)
                Log.d("Tester", "onNicknameFailure: 실행됨")
            }

        })


    }

    private fun getNickname():changeNicknameInfo{
        val aftername = binding.edtNickname.text.toString()

        return changeNicknameInfo(aftername)
    }



    //갤러리 접근을 위한 코드, 최근 api로직 많이 수정됨.
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){

            if(it.resultCode == RESULT_OK && it.data != null){
                val uri = it.data!!.data

                Glide.with(this)
                    .load(uri)
                    .into(binding.imgMyProfile)
            }
        //사진을 직접 보내기 retrofit multipart
        }



    private fun logout() {

        removeJwt()
    }

    private fun withdraw() {
        removeJwt()
        //api보내는건 따로
    }




    //비밀번호 변경 api보고 비밀번호 담아서 요청


}

//사용자 구별=토큰
//어디에든 똑같은 키 code=키값을 관리하는곳 jwt나 auth는 문자열(key값)
//
//페이지에 들어 왔을때 api요청
//
//


