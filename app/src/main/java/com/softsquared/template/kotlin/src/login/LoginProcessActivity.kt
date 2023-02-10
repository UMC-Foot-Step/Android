package com.softsquared.template.kotlin.src.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.config.UserCode
import com.softsquared.template.kotlin.databinding.ActivityLoginProcessBinding
import com.softsquared.template.kotlin.src.login.DataFile.Result
import com.softsquared.template.kotlin.src.login.DataFile.User
import com.softsquared.template.kotlin.src.login.LoginDataSource.NetworkDataSource
import com.softsquared.template.kotlin.src.signup.SignupActivity
import com.softsquared.template.kotlin.util.*

class LoginProcessActivity : BaseActivity<ActivityLoginProcessBinding>(ActivityLoginProcessBinding::inflate) {

    var id_check = false
    var pw_check = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        //로그인 전 제거하고 시작해야 하는 정보
        beforeStartActivity()

        // 비밀번호 4자리 이상 입력하지 않았을 경우에는 버튼 활성화 안되고 4자리 이상 입력한 경우에는 버튼 할성화
        binding.loginBtn.isClickable = false
        binding.loginBtn.isEnabled = false

        binding.etPw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.etPw.length() > 4) {
                    id_check = true
                    id_pw_Check()
                }
                else{
                    id_check = false
                    id_pw_Check()
                }
            }
        })

        binding.etId.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(binding.etId.length()>4){
                    pw_check = true
                    id_pw_Check()
                }
                else{
                    pw_check = false
                    id_pw_Check()
                }
            }

        })

        // 로그인 버튼 입력 -> 성공한 여부는 나중에 추가할 예정
        binding.loginBtn.setOnClickListener{
            login()
        }

        binding.registerBtn.setOnClickListener{
            startregisterActivity()
        }


    }//onCreate

    private fun id_pw_Check(){
        if(id_check)
        {
            if(pw_check){
                binding.loginBtn.setBackgroundResource(R.drawable.solid_button)
                binding.loginBtn.setTextColor(Color.parseColor("#ffffff"))
                binding.loginBtn.isClickable = true
                binding.loginBtn.isEnabled = true

            }
            else{

                binding.loginBtn.setBackgroundResource(R.drawable.login_button)
                binding.loginBtn.isClickable = false
                binding.loginBtn.isEnabled = false

            }
        }else{
            binding.loginBtn.setBackgroundResource(R.drawable.login_button)
            binding.loginBtn.isClickable = false
            binding.loginBtn.isEnabled = false
        }

    }

    private fun login() {
        NetworkDataSource().login(getUser(), object: LoginView{

            override fun onLoginSuccess(code: Int, result: Result?) {
                when(code){
                    200 -> {
                        saveJwt(result!!.grantType + result.jwt)
                        saveRefresh(result!!.grantType + result.refreshJwt)
                        startSuccessActivity()

                    }
                }
            }

            override fun onLoginFailure(message: String?) {
                Toast.makeText(this@LoginProcessActivity,message.toString(),Toast.LENGTH_SHORT)
                    .show()

            }
        })

    }

    private fun getUser(): User {
        var et_pw = findViewById<EditText>(R.id.et_pw)
        var et_id = findViewById<EditText>(R.id.et_id)
        val email = et_id.text.toString()
        val password = et_pw.text.toString()

        return User(email = email, password = password )
    }

    private fun startSuccessActivity(){
        val intent = Intent(this,LoginSuccessActivity::class.java)
        startActivity(intent)

        finish()
    }
    private fun startregisterActivity(){
        val intent = Intent(this,SignupActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun beforeStartActivity(){

        removeJwt()
        removeRefresh()
        removeCheck2()
        removeCheck1()
        removeSignInId()
        removeSignInPw()
        removeSignInNickname()
        removeSignInNicknameCheck()
    }


}




















