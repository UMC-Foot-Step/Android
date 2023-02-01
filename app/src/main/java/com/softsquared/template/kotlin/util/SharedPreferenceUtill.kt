package com.softsquared.template.kotlin.util

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.UserCode
import com.softsquared.template.kotlin.src.login.DataFile.User


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

fun saveRefresh(refresh: String){
    val spf = ApplicationClass.sSharedPreferences
    val editor = spf.edit()

    editor.putString(UserCode.refresh,refresh)
    editor.apply()
}

fun getRefresh():String? = ApplicationClass.sSharedPreferences.getString(UserCode.refresh,null)

fun removeRefresh(){
    val spf = ApplicationClass.sSharedPreferences
    val editor = spf.edit()

    editor.remove(UserCode.refresh)
    editor.apply()
}

fun saveCheck1(status:Boolean){
    val spf = ApplicationClass.sSharedPreferences
    val editor = spf.edit()

    editor.putBoolean(UserCode.check1,status)
    editor.apply()
}

fun getCheck1():Boolean=ApplicationClass.sSharedPreferences.getBoolean(UserCode.check1, false)

fun getCheck2():Boolean=ApplicationClass.sSharedPreferences.getBoolean(UserCode.check2, false)

fun saveCheck2(status: Boolean){
    val spf = ApplicationClass.sSharedPreferences
    val editor = spf.edit()

    editor.putBoolean(UserCode.check2,status)
    editor.apply()
}

fun removeCheck1(){
    val spf = ApplicationClass.sSharedPreferences
    val editor = spf.edit()

    editor.remove(UserCode.check1)
    editor.apply()
}
fun removeCheck2(){
    val spf = ApplicationClass.sSharedPreferences
    val editor = spf.edit()

    editor.remove(UserCode.check2)
    editor.apply()
}

fun SaveSignInId(id: String){
    val spf = ApplicationClass.sSharedPreferences
    val editor = spf.edit()

    editor.putString(UserCode.email,id)
    editor.apply()
}

fun getSignInId():String? = ApplicationClass.sSharedPreferences.getString(UserCode.email,null)

fun removeSignInId(){
    val spf = ApplicationClass.sSharedPreferences
    val editor = spf.edit()

    editor.remove(UserCode.email)
    editor.apply()
}

fun SaveSignInPw(pw: String){
    val spf = ApplicationClass.sSharedPreferences
    val editor = spf.edit()

    editor.putString(UserCode.password,pw)
    editor.apply()
}

fun getSignInPw():String? = ApplicationClass.sSharedPreferences.getString(UserCode.password,null)

fun removeSignInPw(){
    val spf = ApplicationClass.sSharedPreferences
    val editor = spf.edit()

    editor.remove(UserCode.password)
    editor.apply()
}

fun SaveSignInNickname(nickname:String){
    val spf = ApplicationClass.sSharedPreferences
    val editor = spf.edit()

    editor.putString(UserCode.nickname,nickname)
    editor.apply()
}

fun getSignInNickname():String? = ApplicationClass.sSharedPreferences.getString(UserCode.nickname,null)

fun removeSignInNickname(){
    val spf = ApplicationClass.sSharedPreferences
    val editor = spf.edit()

    editor.remove(UserCode.nickname)
    editor.apply()
}











