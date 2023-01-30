package com.softsquared.template.kotlin.src.login.TemporApi

import com.softsquared.template.kotlin.src.login.DataFile.LoginResponse
import com.softsquared.template.kotlin.src.login.DataFile.User
import com.softsquared.template.kotlin.src.main.Example.models.SignUpResponse
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.MypageResponse
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.changeNicknameInfo
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.NicknameResponse
import com.softsquared.template.kotlin.src.signup.DataFile.SignUpForm
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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
    fun join(@Body user: SignUpForm): Call<SignUpResponse>

    @PATCH("my-page/secession")
    fun secession(@Body user: User): Call<SignUpResponse>

    //마이페이지 가져오기
    @GET("/my-page")
    fun getmypage(
        @Header("Authorization") accessToken:String
    ): Call<MypageResponse>


    //마이페이지 정보변경
    @PATCH("/my-page/nickname")
    fun nickname(
        @Body changeNicknameInfo: changeNicknameInfo,
        @Header("Authorization") accessToken: String): Call<NicknameResponse>

    @PATCH("/password")
    fun password(@Body user: User): Call <MypageResponse>


}