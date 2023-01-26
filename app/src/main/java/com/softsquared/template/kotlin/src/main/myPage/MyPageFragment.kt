package com.softsquared.template.kotlin.src.main.myPage

import android.app.Activity.INPUT_METHOD_SERVICE
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.config.UserCode.auth
import com.softsquared.template.kotlin.config.UserCode.jwt
import com.softsquared.template.kotlin.databinding.FragmentMyPageBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import retrofit2.Call


// 메인 - 마이페이지
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                showCustomToast("로그아웃")

                logout()

                startActivity(Intent(activity,MainActivity::class.java))
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
                startActivity(Intent(activity,MainActivity::class.java))

            }
            val noButton = mDialogView.findViewById<Button>(R.id.btn_no_with)
            noButton.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }



    }//onCreate


    //갤러리 접근을 위한 코드, 최근 api로직 많이 수정됨.
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){

            if(it.resultCode == RESULT_OK && it.data != null){
                val uri = it.data!!.data

                Glide.with(this)
                    .load(uri)
                    .into(binding.imgMyProfile)
            }
        }






    private fun logout() {
        val spf = activity?.getSharedPreferences(auth , AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        context?.let { showLoadingDialog(it) }
        //로그아웃 api호출
        editor.remove(jwt)
        editor.apply()
    }

    private fun withdraw() {
        val spf = activity?.getSharedPreferences(auth , AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        context?.let { showLoadingDialog(it) }
        //회원탈퇴 api호출
        editor.remove(jwt)
        editor.apply()
    }


    //비밀번호 변경 api보고 비밀번호 담아서 요청


}

//사용자 구별=토큰
//어디에든 똑같은 키 code=키값을 관리하는곳 jwt나 auth는 문자열(key값)
//
//페이지에 들어 왔을때 api요청
//
//


