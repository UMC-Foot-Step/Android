package com.softsquared.template.kotlin.src.main.myPage

interface PasswordView {
    fun onPasswordSuccess(code : Int, result : String)
    fun onPasswordFailure(message:String)
}