package com.footstep.dangbal.kotlin.src.signup

interface SignUpView {
    fun onSignUpSuccess(code: Int, result: String)
    fun onSignUpFailure(message: String?)

}