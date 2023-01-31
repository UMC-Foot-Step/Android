package com.softsquared.template.kotlin.src.main.myPage

interface SecessionView {
    fun onSecessionSuccess(code : Int, result : String)
    fun onSecessionFailure(message:String)
}