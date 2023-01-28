package com.softsquared.template.kotlin.src.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityLoginSuccessBinding
import com.softsquared.template.kotlin.src.main.MainActivity

class LoginSuccessActivity : BaseActivity<ActivityLoginSuccessBinding>(ActivityLoginSuccessBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_success)

        var start_btn = findViewById<Button>(R.id.start_btn)

        start_btn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}




