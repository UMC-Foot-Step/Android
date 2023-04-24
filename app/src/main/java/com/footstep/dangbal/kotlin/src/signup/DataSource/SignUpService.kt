package com.footstep.dangbal.kotlin.src.signup.DataSource

import android.util.Log
import com.footstep.dangbal.kotlin.config.ApplicationClass
import com.footstep.dangbal.kotlin.src.login.TemporApi.RetrofitInterface
import com.footstep.dangbal.kotlin.src.main.Example.models.SignUpResponse

import com.footstep.dangbal.kotlin.src.signup.DataFile.SignUpForm

import com.footstep.dangbal.kotlin.src.signup.SignUpView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpService {

    private val api = ApplicationClass.sRetrofit.create(RetrofitInterface::class.java)

    fun signUp(user : SignUpForm, signupView: SignUpView){
        Log.d("Tester", "signUp: 여까지는?어때?")
        val signUpService = api.join(user)

        signUpService.enqueue(object : Callback<SignUpResponse> {
            override fun onFailure(call: Call<SignUpResponse>, t: Throwable){
                signupView.onSignUpFailure(t.message)

            }


            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                val signupResponse: SignUpResponse = response.body()!!
                Log.d("Tester", "onResponse: 성공?${signupResponse}")
                when (val code = signupResponse.code){
                    200 -> signupView.onSignUpSuccess(code, signupResponse.result)
                    else -> signupView.onSignUpFailure(signupResponse.message)
                }
            }
        })
    }
}


