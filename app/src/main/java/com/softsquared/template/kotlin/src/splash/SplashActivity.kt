package com.softsquared.template.kotlin.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.widget.TextView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.config.UserCode
import com.softsquared.template.kotlin.databinding.ActivitySplashBinding
import com.softsquared.template.kotlin.src.login.DataFile.Result
import com.softsquared.template.kotlin.src.login.LoginDataSource.NetworkDataSource
import com.softsquared.template.kotlin.src.login.LoginProcessActivity
import com.softsquared.template.kotlin.src.login.LoginView
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.onboarding.OnboardingActivity
import com.softsquared.template.kotlin.util.removeRefresh

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    lateinit var textView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        textView = findViewById(R.id.text1)

        val textData:String = textView.text.toString()
        val builder = SpannableStringBuilder(textData)
        val sizeSpan = RelativeSizeSpan(1.4f)
        builder.setSpan(sizeSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = builder

        autoLogin()

    }


    //자동로그인 기능 but 어째서인지 Splash가 너무 빨리 끝남..
    fun autoLogin(){
        Log.d("Tester", "autoLogin: ")
        val spf = ApplicationClass.sSharedPreferences
        val refresh = spf.getString(UserCode.refresh,null)

        // removeRefresh()

        if(refresh == null) {
            Log.d("Tester", "autoLogin: ddd")
            startActivity(OnboardingActivity::class.java)
        }
        else
            NetworkDataSource().autoLogin(refresh,object : LoginView {
                override fun onLoginSuccess(code: Int, result: Result?) {
                    startActivity(MainActivity::class.java)
                }

                override fun onLoginFailure(message: String?) {
                    Log.d("Tester", "onLoginFailure: $message")
                    showCustomToast(message!!)
                    startActivity(LoginProcessActivity::class.java)
                }
            })
    }
    private fun startActivity(cls: Class<*>){
        Handler(Looper.getMainLooper())
            .postDelayed({
                startActivity(Intent(this,cls))
                finish()
            },1500)
    }

}