package com.softsquared.template.kotlin.src.signup.DataSource

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.login.DataFile.LoginResponse
import com.softsquared.template.kotlin.src.login.DataFile.User
import com.softsquared.template.kotlin.src.login.TemporApi.RetrofitInterface
import com.softsquared.template.kotlin.src.main.Example.models.SignUpResponse

import com.softsquared.template.kotlin.src.signup.DataFile.SignUpForm

import com.softsquared.template.kotlin.src.signup.SignUpView
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


