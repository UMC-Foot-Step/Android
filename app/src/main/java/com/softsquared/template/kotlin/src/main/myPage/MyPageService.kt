package com.softsquared.template.kotlin.src.main.myPage

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.login.DataFile.User
import com.softsquared.template.kotlin.src.login.LoginDataSource.getRetrofit
import com.softsquared.template.kotlin.src.login.TemporApi.RetrofitInterface
import com.softsquared.template.kotlin.src.main.myPage.mypageDataFile.NicknameResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageService(val nicknameView: NicknameView) {

    private val nicknameapi = ApplicationClass.sRetrofit.create(RetrofitInterface::class.java)

    fun trygetnickname(user: User, nicknameView: NicknameView) {
        val nicknameService = nicknameapi.nickname(user)

        nicknameService.enqueue(object : Callback<NicknameResponse> {

            override fun onFailure(call: Call<NicknameResponse>, t: Throwable) =
                nicknameView.onNicknameFailure(t.message.toString())

            override fun onResponse(
                call: Call<NicknameResponse>,
                response: Response<NicknameResponse>
            ) {
                val resp = response.body() as NicknameResponse
                when (resp.code) {
                    100 -> nicknameView.onNicknameSuccess()
                    else -> nicknameView.onNicknameFailure(resp.message)
                }

            }
        })

    }

    fun trychangenickname(user:User, nicknameView: NicknameView){
        val changeservice = nicknameapi.nickname(user)

        changeservice.enqueue(object : Callback<NicknameResponse>{ // 임시, Nicknamechangeresponse로 바꿀 예정
            override fun onFailure(call: Call<NicknameResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<NicknameResponse>,
                response: Response<NicknameResponse>
            ) {

            }
        })
    }




















//    fun gallery(user: User){
//
//    }
}