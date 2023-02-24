package com.softsquared.template.kotlin.src.main.myPage

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.login.TemporApi.RetrofitInterface
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.NicknameResponse
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.changePasswordInfo
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.passwordResponse
import com.softsquared.template.kotlin.util.LoadingDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordService {

    private val api = ApplicationClass.sRetrofit.create(RetrofitInterface::class.java)

    fun trychangepassword(accessToken: String, changePasswordInfo: changePasswordInfo, passwordView: PasswordView){
        val passwordService = api.password(changePasswordInfo,accessToken)
        passwordService.enqueue(object : Callback<passwordResponse> {
            override fun onResponse(
                call: Call<passwordResponse>,
                response: Response<passwordResponse>
            ) {
                if(response.isSuccessful && response.code() == 200){
                    val passwordResponse: passwordResponse = response.body()!!
                    Log.d("Tester", "onResponse: $passwordResponse")
                    when (val code = passwordResponse.code){
                        200 ->  passwordView.onPasswordSuccess(code,passwordResponse.result!!)
                        else -> passwordView.onPasswordFailure(response.message())
                    }
                }

            }

            override fun onFailure(call: Call<passwordResponse>, t: Throwable) {
                passwordView.onPasswordFailure(t.message?: "실패")
                Log.d("Tester", "onFailure: dddddd")
            }

        })



    }


}