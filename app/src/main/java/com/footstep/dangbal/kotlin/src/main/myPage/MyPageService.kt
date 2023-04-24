package com.footstep.dangbal.kotlin.src.main.myPage

import android.util.Log
import com.footstep.dangbal.kotlin.config.ApplicationClass
import com.footstep.dangbal.kotlin.src.login.TemporApi.RetrofitInterface
import com.footstep.dangbal.kotlin.src.main.myPage.mypageResponseFile.MypageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageService {

    private val mypageapi = ApplicationClass.sRetrofit.create(RetrofitInterface::class.java)

    fun tryGetMyPage(accessToken : String,MyPageView : MyPageView) {
        val mypageservice = mypageapi.getmypage(accessToken)
        mypageservice.enqueue(object : Callback<MypageResponse>{
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



//    fun gallery(user: User){
//
//    }
}