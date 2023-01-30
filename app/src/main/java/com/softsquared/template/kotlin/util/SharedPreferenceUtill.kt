package com.softsquared.template.kotlin.util

import androidx.appcompat.app.AppCompatActivity
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.UserCode


fun saveJwt(jwt:String){

    val spf = ApplicationClass.sSharedPreferences
    val editor = spf.edit()

    editor.putString(UserCode.jwt,jwt)
    editor.apply()
}

fun getJwt():String?= ApplicationClass.sSharedPreferences.getString(UserCode.jwt, null)

fun removeJwt() {

    val spf = ApplicationClass.sSharedPreferences
    val editor = spf.edit()

    editor.remove(UserCode.jwt)
    editor.apply()
}