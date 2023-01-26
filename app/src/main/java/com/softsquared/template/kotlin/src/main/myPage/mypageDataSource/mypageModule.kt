package com.softsquared.template.kotlin.src.main.myPage.mypageDataSource

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL =""

fun getMyPageRetrofit():Retrofit{
    val mpretrofit = Retrofit.Builder()
        .baseUrl(com.softsquared.template.kotlin.src.login.LoginDataSource.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return mpretrofit
}