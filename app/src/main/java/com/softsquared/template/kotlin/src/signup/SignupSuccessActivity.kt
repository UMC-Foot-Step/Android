package com.softsquared.template.kotlin.src.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivitySignupSuccessBinding
import com.softsquared.template.kotlin.src.login.DataFile.Result
import com.softsquared.template.kotlin.src.login.DataFile.User
import com.softsquared.template.kotlin.src.login.LoginDataSource.NetworkDataSource
import com.softsquared.template.kotlin.src.login.LoginView
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.util.getSignInId
import com.softsquared.template.kotlin.util.getSignInPw
import com.softsquared.template.kotlin.util.saveJwt
import com.softsquared.template.kotlin.util.saveRefresh

class SignupSuccessActivity : BaseActivity<ActivitySignupSuccessBinding>(ActivitySignupSuccessBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.nextBtn.setOnClickListener {
            login()
        }

    }//onCreate

    private fun login(){
        NetworkDataSource().login(getUser(), object : LoginView{
            override fun onLoginSuccess(code: Int, result: Result?) {
                saveJwt(result!!.grantType + result.jwt)
                saveRefresh(result!!.grantType + result.refreshJwt)
                startMainActivity()
            }

            override fun onLoginFailure(message: String) {
                Toast.makeText(this@SignupSuccessActivity,message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun getUser(): User{
        val email = getSignInId()
        val password = getSignInPw()

        return User(email = email!!, password = password!!)
    }

    private fun startMainActivity(){
        val intent = Intent(this,MainActivity::class.java)
        finishAffinity()
        startActivity(intent)

    }













}