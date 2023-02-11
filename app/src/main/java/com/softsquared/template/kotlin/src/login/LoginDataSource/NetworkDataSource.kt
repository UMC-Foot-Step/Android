package com.softsquared.template.kotlin.src.login.LoginDataSource

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.login.DataFile.LoginResponse
import com.softsquared.template.kotlin.src.login.DataFile.User
import com.softsquared.template.kotlin.src.login.LoginView
import com.softsquared.template.kotlin.src.login.TemporApi.RetrofitInterface
import com.softsquared.template.kotlin.src.main.Example.models.SignUpResponse
import com.softsquared.template.kotlin.src.main.Example.models.UserResponse
import com.softsquared.template.kotlin.src.signup.SignUpView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkDataSource {
    companion object TAG{
        val success = "SIGNUP?SUCCESS"
        val fail = "SIGNUP/FAILURE"
    }
    private val api = ApplicationClass.sRetrofit.create(RetrofitInterface::class.java)

    fun login(user: User, loginView: LoginView){
        api.login(user).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if(response.isSuccessful && response.code() == 200){
                    val loginResponse: LoginResponse = response.body()!!
                    Log.d("Tester", "onResponse: ${loginResponse}")
                    when (val code = loginResponse.code){

                        200 -> loginView.onLoginSuccess(code,loginResponse.result!!)
                        else -> loginView.onLoginFailure(response.message())
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable)
                = loginView.onLoginFailure(t.message)

        })
    }

    fun autoLogin(jwt:String,loginView: LoginView){
        api.reissue(jwt).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful && response.code() == 200){
                    val loginResponse: LoginResponse = response.body()!!

                    when(val code = loginResponse.code){
                        200->loginView.onLoginSuccess(code,null)
                        else -> loginView.onLoginFailure(response.message())
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable)
                    = loginView.onLoginFailure(t.message)


        })



    }


}


