package com.softsquared.template.kotlin.src.signup

interface SignUpView {
    fun onSignUpSuccess()
    fun onSignUpFailure(message: String?)
}