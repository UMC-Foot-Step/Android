package com.softsquared.template.kotlin.src.main.myPage

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.login.TemporApi.RetrofitInterface
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.MypageResponse
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.Nickname
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.NicknameResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NicknameService {

   private val api = ApplicationClass.sRetrofit.create(RetrofitInterface::class.java)



   fun trygetNickname(accessToken : String, nickname: Nickname, nicknameView: NicknameView){
      val nicknameService = api.nickname(nickname, accessToken)
      nicknameService.enqueue(object : Callback<NicknameResponse>{
         override fun onResponse(
            call: Call<NicknameResponse>,
            response: Response<NicknameResponse>
         ) {
            if(response.isSuccessful && response.code() == 200){
               val nicknameResponse: NicknameResponse = response.body()!!
               when (val code = nicknameResponse.code){
                  200 ->  nicknameView.onNicknameSuccess(code,nicknameResponse.result!!)
                  else -> nicknameView.onNicknameFailure(response.message())
               }
            }
         }
         override fun onFailure(call: Call<NicknameResponse>, t: Throwable) {
            nicknameView.onNicknameFailure(t.message?: "실패")
         }

      })

   }



}