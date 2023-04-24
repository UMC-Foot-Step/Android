package com.footstep.dangbal.kotlin.src.login

import com.footstep.dangbal.kotlin.src.login.DataFile.Result

interface LoginView {
    fun onLoginSuccess(code : Int, result : Result? = null)
    fun onLoginFailure(message: String)
}