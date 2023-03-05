package com.softsquared.template.kotlin.src.login.TemporApi

import com.softsquared.template.kotlin.src.login.DataFile.LoginResponse
import com.softsquared.template.kotlin.src.login.DataFile.User
import com.softsquared.template.kotlin.src.main.Example.models.SignUpResponse
import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.*
import com.softsquared.template.kotlin.src.signup.DataFile.SignUpForm
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitInterface {
    //로그인

    @POST("/login")
    fun login(@Body user: User): Call<LoginResponse>

    @POST("/reissue")
    fun reissue(@Header("RefreshToken") refreshToken: String
    ): Call<LoginResponse>


    //회원가입
    @POST("/join")
    fun join(@Body user: SignUpForm): Call<SignUpResponse>

    @PATCH("my-page/secession")
    fun secession(
        @Header("Authorization") accessToken: String,
        @Header("RefreshToken") refreshToken: String
    ): Call<SecessionResponse>

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

    @PATCH("/my-page/password")
    fun password(
        @Body changePasswordInfo: changePasswordInfo,
        @Header("Authorization") accessToken: String): Call <passwordResponse>


    @Multipart
    @PATCH("/my-page/profile")
    fun profile(
        @Header("Authorization") accessToken: String,
        @Part file: MultipartBody.Part
    ): Call<ProfileResponse>




}