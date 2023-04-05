package com.softsquared.template.kotlin.src.main.myPage

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.login.TemporApi.RetrofitInterface
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.changeNicknameInfo
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.NicknameResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NicknameService {

   private val api = ApplicationClass.sRetrofit.create(RetrofitInterface::class.java)

   fun trychangeNickname(accessToken : String, changeNicknameInfo: changeNicknameInfo, nicknameView: NicknameView){
      val nicknameService = api.nickname(changeNicknameInfo, accessToken)
      Log.d("Tester", "trychangeNickname: $changeNicknameInfo")
      nicknameService.enqueue(object : Callback<NicknameResponse>{
         override fun onResponse(
            call: Call<NicknameResponse>,
            response: Response<NicknameResponse>
         ) {
            if(response.isSuccessful && response.code() == 200){
               val nicknameResponse: NicknameResponse = response.body()!!
               Log.d("Tester", "onResponse: 실행됨${nicknameResponse}")
               when (val code = nicknameResponse.code){
                  200 ->  nicknameView.onNicknameSuccess(code,nicknameResponse.result!!)
                  else -> nicknameView.onNicknameFailure(nicknameResponse.message)
               }
            }
         }
         override fun onFailure(call: Call<NicknameResponse>, t: Throwable) {
            nicknameView.onNicknameFailure(t.message?: "실패")
         }

      })

   }



}