package com.softsquared.template.kotlin.src.signup

import android.os.Bundle
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMainPostBinding

class SignupInfoActivity : BaseActivity<ActivityMainPostBinding>(ActivityMainPostBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_info)

    }
}