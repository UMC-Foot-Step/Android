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
import com.softsquared.template.kotlin.config.UserCode.auth
import com.softsquared.template.kotlin.config.UserCode.auth2
import com.softsquared.template.kotlin.databinding.ActivityMainPostBinding
import com.softsquared.template.kotlin.src.login.DataFile.Result
import com.softsquared.template.kotlin.src.login.DataFile.User
import com.softsquared.template.kotlin.src.login.LoginDataSource.NetworkDataSource

class LoginProcessActivity : BaseActivity<ActivityMainPostBinding>(ActivityMainPostBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_process)

        var et_pw = findViewById<EditText>(R.id.et_pw)
        var login_btn = findViewById<Button>(R.id.login_btn)


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
            showCustomToast("누름")
        }
    }

    private fun login() {
        NetworkDataSource().login(getUser(), object: LoginView{

            override fun onLoginSuccess(code: Int, result: Result?) {
                Log.d("tester2", "onLoginSuccess: 실행됨$result")
                when(code){
                    200 -> {
                        saveJwt2(result!!.jwt)
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
        val email = R.id.et_id.toString()
        val password = R.id.et_pw.toString()

        return User(email = email, password = password )
    }

    private fun startSuccessActivity(){
        val intent = Intent(this,LoginSuccessActivity::class.java)
        startActivity(intent)
        showCustomToast("실행됨")

        finish()
    }

    //int형 jwt
    private fun saveJwt(jwt: Int) {
        val spf = getSharedPreferences(auth , MODE_PRIVATE)
        val editor = spf.edit()

        editor.putInt("jwt", jwt)
        editor.apply()
    }

    //string형 jwt
    private fun saveJwt2(jwt:String){
        val spf = getSharedPreferences(auth2, MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString(UserCode.jwt,jwt)
        editor.apply()
    }
}




















