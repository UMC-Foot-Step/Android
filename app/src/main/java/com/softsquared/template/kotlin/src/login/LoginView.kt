package com.softsquared.template.kotlin.src.login

import com.softsquared.template.kotlin.src.login.DataFile.Result

interface LoginView {
    fun onLoginSuccess(code : Int, result : Result? = null)
    fun onLoginFailure(message: String?)
}