package com.footstep.dangbal.kotlin.src.main.myPage

interface PasswordView {
    fun onPasswordSuccess(code : Int, result : String)
    fun onPasswordFailure(message:String)
}