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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_process)

        var et_pw = findViewById<EditText>(R.id.et_pw)
        var login_btn = findViewById<Button>(R.id.login_btn)
        var register_btn = findViewById<Button>(R.id.register_btn)

        //로그인 전 제거하고 시작해야 하는 정보
        beforeStartActivity()

        // 비밀번호 4자리 이상 입력하지 않았을 경우에는 버튼 활성화 안되고 4자리 이상 입력한 경우에는 버튼 할성화
        login_btn.isClickable = false
        login_btn.isEnabled = false

        et_pw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (et_pw.length() < 4) {
                    login_btn.setBackgroundResource(R.drawable.login_button)
                    login_btn.isClickable = false
                    login_btn.isEnabled = false
                } else {
                    login_btn.setBackgroundResource(R.drawable.solid_button)
                    login_btn.setTextColor(Color.parseColor("#ffffff"))
                    login_btn.isClickable = true
                    login_btn.isEnabled = true
                }
            }

        })



        // 로그인 버튼 입력 -> 성공한 여부는 나중에 추가할 예정
        login_btn.setOnClickListener{
            login()
        }

        register_btn.setOnClickListener{
            startregisterActivity()
        }






    }

    private fun login() {
        NetworkDataSource().login(getUser(), object: LoginView{

            override fun onLoginSuccess(code: Int, result: Result?) {
                when(code){
                    200 -> {
                        saveJwt(result!!.grantType + result.jwt)
                        startSuccessActivity()
                        Log.d("Tester", "onLoginSuccess: 실행됨")
                    }
                }
            }

            override fun onLoginFailure(message: String?) {
                Toast.makeText(this@LoginProcessActivity,message.toString(),Toast.LENGTH_SHORT)
                    .show()
                Log.d("Tester", "onLoginFailure: dd")
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
        removeCheck2()
        removeCheck1()
        removeSignInId()
        removeSignInPw()
        removeSignInNickname()
    }


}




















