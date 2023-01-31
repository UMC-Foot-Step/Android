package com.softsquared.template.kotlin.src.main.myPage


import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.login.TemporApi.RetrofitInterface
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.NicknameResponse
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.SecessionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecessionService {

    private val api = ApplicationClass.sRetrofit.create(RetrofitInterface::class.java)

    fun trySecession(assessToken : String, refreshToken : String, secessionView: SecessionView){
        val secessionService = api.secession(assessToken,refreshToken)
        secessionService.enqueue(object : Callback<SecessionResponse> {
            override fun onResponse(
                call: Call<SecessionResponse>,
                response: Response<SecessionResponse>
            ) {
                if(response.isSuccessful && response.code() == 200){
                    val secessionResponse: SecessionResponse = response.body()!!
                    Log.d("Tester", "onResponse: 실행됨${secessionResponse}")
                    when (val code = secessionResponse.code){
                        200 ->  secessionView.onSecessionSuccess(code,secessionResponse.result!!)
                        else -> secessionView.onSecessionFailure(response.message())
                    }
                }

            }
            override fun onFailure(call: Call<SecessionResponse>, t: Throwable) {
                secessionView.onSecessionFailure(t.message?: "통신오류")
            }

        })

    }

}