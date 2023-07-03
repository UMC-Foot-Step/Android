package com.footstep.dangbal.kotlin.src.login

interface FindPasswordView {
    fun onFindSuccess(code:Int, result:String)
    fun onFindFailure(message:String)

}