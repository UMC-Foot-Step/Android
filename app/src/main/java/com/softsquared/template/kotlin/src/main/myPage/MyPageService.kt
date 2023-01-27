package com.softsquared.template.kotlin.src.main.myPage

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.XAccessTokenInterceptor
import com.softsquared.template.kotlin.src.login.DataFile.User
import com.softsquared.template.kotlin.src.login.TemporApi.RetrofitInterface
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.MypageResponse
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.NicknameResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageService() {

    private val mypageapi = ApplicationClass.sRetrofit.create(RetrofitInterface::class.java)


    fun tryGetMyPage(MyPageView : MyPageView) {
        ApplicationClass.sSharedPreferences.edit().putString(ApplicationClass.X_ACCESS_TOKEN, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImZvb3RzdGVwQG5hdmVyLmNvbSIsImlhdCI6MTY3NDY2MDA5MSwiZXhwIjoxNjc0OTYyNDkxfQ.W7MNMFI43SPbcw5pLhpbsuic0_nCDRcqHKPgEipV9ko").apply()
        mypageapi.getmypage().enqueue(object : Callback<MypageResponse>{
            override fun onFailure(call: Call<MypageResponse>, t: Throwable) {
                MyPageView.onMyPageFailure(t.message ?: "실패")

            }

            override fun onResponse(
                call: Call<MypageResponse>,
                response: Response<MypageResponse>
            ) {
                if(response.isSuccessful && response.code() == 200){
                    val mypageResponse:MypageResponse= response.body()!!
                    Log.d("tester", "onResponse: 실행됨${mypageResponse}")
                    when (val code = mypageResponse.code){
                        200 -> MyPageView.onMyPageSuccess(code,mypageResponse.result!!)
                        else -> MyPageView.onMyPageFailure(response.message())
                    }
                }
            }
        })
    }

    fun trychangenickname(user:User, nicknameView: NicknameView){
        val changeservice = mypageapi.nickname(user)

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