package com.softsquared.template.kotlin.src.main.myPage

interface ProfileView {
    fun onProfileSuccess(code : Int, result : String)
    fun onProfileFailure(message:String)
}