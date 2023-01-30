package com.softsquared.template.kotlin.src.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMainPostBinding
import com.softsquared.template.kotlin.databinding.ActivitySignupSuccessBinding
import com.softsquared.template.kotlin.src.login.LoginProcessActivity

class SignupSuccessActivity : BaseActivity<ActivitySignupSuccessBinding>(ActivitySignupSuccessBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_success)

        var next = findViewById<Button>(R.id.next_btn)

        next.setOnClickListener {
            val intent = Intent(this,LoginProcessActivity::class.java)
            startActivity(intent)
        }

    }
}