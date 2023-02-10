package com.softsquared.template.kotlin.config

object UserCode {
    //로그인 정보
    // 이든 | jwt 데이터 값 SP Key 값에 맞는 값으로 변경 (2023. 02. 01)
    const val jwt = "accessToken"
    const val id = "id"
    const val refresh = "refresh"

    //회원가입 정보
    const val email = "email"
    const val password = "password"
    const val nickname = "nickname"


    //회원가입 동의정보 스크롤
    const val check1 = "check1"
    const val check2 = "check2"
    const val nicknamecheck = "nicknamecheck"
}