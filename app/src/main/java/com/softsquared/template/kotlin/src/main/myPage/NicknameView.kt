package com.softsquared.template.kotlin.src.main.myPage

interface NicknameView {
    fun onNicknameSuccess(code : Int, result : String)
    fun onNicknameFailure(message:String)
}