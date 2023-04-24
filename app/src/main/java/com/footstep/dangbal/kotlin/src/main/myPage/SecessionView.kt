package com.footstep.dangbal.kotlin.src.main.myPage

interface SecessionView {
    fun onSecessionSuccess(code : Int, result : String)
    fun onSecessionFailure(message:String)
}