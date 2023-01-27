package com.softsquared.template.kotlin.src.login.TemporApi

import com.softsquared.template.kotlin.src.login.DataFile.LoginResponse
import com.softsquared.template.kotlin.src.login.DataFile.User
import com.softsquared.template.kotlin.src.main.Example.models.SignUpResponse
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.MypageResponse
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.NicknameResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface RetrofitInterface {
    //로그인

    @POST("/login")
    fun login(@Body user: User): Call<LoginResponse>

    @POST("/reissue")
    fun reissue(@Body user: User): Call<LoginResponse>



    //회원가입
    @POST("/join")
    fun join(@Body user: User): Call<SignUpResponse>

    @PATCH("my-page/secession")
    fun secession(@Body user: User): Call<SignUpResponse>

    //마이페이지 가져오기
    @GET("/my-page")
    fun getmypage(): Call<MypageResponse>


    //마이페이지 정보변경
    @PATCH("/nickname")
    fun nickname(@Body user: User): Call<NicknameResponse>

    @PATCH("/password")
    fun password(@Body user: User): Call <MypageResponse>


}