package com.softsquared.template.kotlin.src.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityOnboardingBinding
import com.softsquared.template.kotlin.src.login.LoginProcessActivity

class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(ActivityOnboardingBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        var onboarding1img = findViewById<ImageView>(R.id.onboarding1img)
        var onboarding2img = findViewById<ImageView>(R.id.onboarding2img)
        var onboarding3img = findViewById<ImageView>(R.id.onboarding3img)
        var onboarding1txt = findViewById<TextView>(R.id.onboarding1txt)
        var onboarding2txt = findViewById<TextView>(R.id.onboarding2txt)
        var onboarding3txt = findViewById<TextView>(R.id.onboarding3txt)
        var onboarding_btn = findViewById<Button>(R.id.onboarding_btn)
        var count = 0

        onboarding_btn.setOnClickListener{
            count++
            if(count == 1){
                onboarding1img.visibility = View.INVISIBLE
                onboarding2img.visibility = View.VISIBLE
                onboarding1txt.visibility = View.INVISIBLE
                onboarding2txt.visibility = View.VISIBLE
            } else if(count == 2){
                onboarding2img.visibility = View.INVISIBLE
                onboarding3img.visibility = View.VISIBLE
                onboarding2txt.visibility = View.INVISIBLE
                onboarding3txt.visibility = View.VISIBLE
                onboarding_btn.text = "시작하기"
            } else{
                startActivity(Intent(this, LoginProcessActivity::class.java))
            }
        }

    }//onCrete



}