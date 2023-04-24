package com.footstep.dangbal.kotlin.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.widget.TextView
import com.footstep.dangbal.kotlin.R
import com.footstep.dangbal.kotlin.config.BaseActivity
import com.footstep.dangbal.kotlin.databinding.ActivitySplashBinding
import com.footstep.dangbal.kotlin.src.login.DataFile.Result
import com.footstep.dangbal.kotlin.src.login.LoginDataSource.NetworkDataSource
import com.footstep.dangbal.kotlin.src.login.LoginProcessActivity
import com.footstep.dangbal.kotlin.src.login.LoginView
import com.footstep.dangbal.kotlin.src.main.MainActivity
import com.footstep.dangbal.kotlin.src.onboarding.OnboardingActivity
import com.footstep.dangbal.kotlin.util.getRefresh
import com.footstep.dangbal.kotlin.util.saveRefresh

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

        Log.d("Tester", "autoLogin: dd")
        val refresh = getRefresh()
        Log.d("Tester", "autoLogin: $refresh")

        if(refresh == null) {
            Log.d("Tester", "autoLogin: ddd")
            startActivity(OnboardingActivity::class.java)
        }
        else
            NetworkDataSource().autoLogin(refresh, object : LoginView {
                override fun onLoginSuccess(code: Int, result: Result?) {
                    Log.d("Tester", "onLoginSuccess: $result")
                    startActivity(MainActivity::class.java)
                    result?.let { saveRefresh(result.grantType + result.refreshJwt) }
                    //let,apply,run : !!=위험, 코틀린에 맞는 문법을 사용해 위험요소 제거
                }

                override fun onLoginFailure(message: String) {
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

//백그라운드 실행 없이 바로 자동로그인 실행되는 방법 찾기














