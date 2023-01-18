package com.softsquared.template.kotlin.src.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMainPostBinding

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
            startActivity(Intent(this, LoginSuccessActivity::class.java))
        }
    }


}