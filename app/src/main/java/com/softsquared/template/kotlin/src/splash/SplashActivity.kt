package com.softsquared.template.kotlin.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.widget.TextView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivitySplashBinding
import com.softsquared.template.kotlin.src.onboarding.OnboardingActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    lateinit var textView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        textView = findViewById(R.id.text1)

        val textData:String = textView.text.toString()
        val builder = SpannableStringBuilder(textData)
        val sizeSpan = RelativeSizeSpan(1.4f)
        builder.setSpan(sizeSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = builder

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }, 1500)
    }
}