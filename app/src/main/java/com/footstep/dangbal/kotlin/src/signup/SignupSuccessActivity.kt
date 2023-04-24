package com.footstep.dangbal.kotlin.src.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.footstep.dangbal.kotlin.config.BaseActivity
import com.footstep.dangbal.kotlin.databinding.ActivitySignupSuccessBinding
import com.footstep.dangbal.kotlin.src.login.DataFile.Result
import com.footstep.dangbal.kotlin.src.login.DataFile.User
import com.footstep.dangbal.kotlin.src.login.LoginDataSource.NetworkDataSource
import com.footstep.dangbal.kotlin.src.login.LoginView
import com.footstep.dangbal.kotlin.src.main.MainActivity
import com.footstep.dangbal.kotlin.util.getSignInId
import com.footstep.dangbal.kotlin.util.getSignInPw
import com.footstep.dangbal.kotlin.util.saveJwt
import com.footstep.dangbal.kotlin.util.saveRefresh

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