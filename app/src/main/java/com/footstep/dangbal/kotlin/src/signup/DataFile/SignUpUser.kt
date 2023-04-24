package com.footstep.dangbal.kotlin.src.signup.DataFile

import java.io.Serializable

data class SignUpUser(
    val email : String,
    val password : String
): Serializable


//관리를 위한 기준, 상속을 이용