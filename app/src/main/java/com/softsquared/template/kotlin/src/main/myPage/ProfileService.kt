package com.softsquared.template.kotlin.src.main.myPage

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.login.TemporApi.RetrofitInterface
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.ProfileResponse
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.SecessionResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileService {

    private val api = ApplicationClass.sRetrofit.create(RetrofitInterface::class.java)

    fun tryChangeProfile(accessToken : String, profile : MultipartBody.Part, profileView: ProfileView){
        val profileService = api.profile(accessToken , profile)
            profileService.enqueue(object : Callback<ProfileResponse>{
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    val profileResponse: ProfileResponse = response.body()!!
                    Log.d("Tester", "onResponse: 실행됨${profileResponse}")
                    when (val code = profileResponse.code){
                        200 ->  profileView.onProfileSuccess(code,profileResponse.result!!)
                        else -> profileView.onProfileFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    profileView.onProfileFailure(t.message?:"통신오류")
                }

            })

    }
}