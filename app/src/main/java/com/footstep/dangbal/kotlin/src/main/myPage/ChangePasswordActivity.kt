package com.footstep.dangbal.kotlin.src.main.myPage

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.footstep.dangbal.kotlin.config.BaseActivity
import com.footstep.dangbal.kotlin.databinding.ActivityChangePasswordBinding
import com.footstep.dangbal.kotlin.src.login.LoginProcessActivity
import com.footstep.dangbal.kotlin.src.main.myPage.mypageResponseFile.changePasswordInfo
import com.footstep.dangbal.kotlin.util.getJwt

class ChangePasswordActivity: BaseActivity<ActivityChangePasswordBinding>(ActivityChangePasswordBinding::inflate){

    var present_password = false
    var after_password = false

    private val accessToken : String by lazy{
        val jwt = getJwt()

        if(jwt == null){
            startActivity(Intent(this, LoginProcessActivity::class.java))
            finish()

            //flag clear -> 로그인 화면 넘어가면 이전 activity삭제
            return@lazy ""
        }

        jwt
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        with(binding){
            // 뒤로가기 버튼 누르기
            btnBack.setOnClickListener {
                finish()
            }

            etPresentPassword.addTextChangedListener (object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if(etPresentPassword.length()>5){
                        present_password = true
                        checknext()
                    }
                    else{
                        present_password = false
                        checknext()
                    }
                }

            })

            etAfterPassword.addTextChangedListener (object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    if(etAfterPassword.length()>5){
                        after_password = true
                        checknext()
                    }
                    else{
                        after_password = false
                        checknext()
                    }
                }

            })

            btnModifyPassword.setOnClickListener {

                checkPresentPassword()

            }


        }

    }//onCreate

    private fun checknext(){

        if(present_password){
            if(after_password){
                binding.btnModifyPassword.isEnabled = true
                binding.btnModifyPassword.setTextColor(Color.parseColor("#ffffff"))
            }
            else{
                binding.btnModifyPassword.isEnabled = false
                binding.btnModifyPassword.setTextColor(Color.parseColor("#000000"))
            }
        }
        else {
            binding.btnModifyPassword.isEnabled = false
            binding.btnModifyPassword.setTextColor(Color.parseColor("#000000"))
        }

    }

    private fun checkPresentPassword(){

        PasswordService().trychangepassword(accessToken, getPassword(),object : PasswordView{
            override fun onPasswordSuccess(code: Int, result: String) {
                when(code){
                    200->{
                        showCustomToast(result)
                        startChangePassword()
                    }
                }
            }

            override fun onPasswordFailure(message: String) {
                showCustomToast(message)
            }

        })




    }

    private fun getPassword():changePasswordInfo{
        val current = binding.etPresentPassword.text.toString()
        val after = binding.etAfterPassword.text.toString()

        return changePasswordInfo(after, current)
    }
    private fun startChangePassword(){
        val intent = Intent(this@ChangePasswordActivity,LoginProcessActivity::class.java)
        startActivity(intent)
        showCustomToast("재 로그인이 필요합니다")
        finish()
    }



}