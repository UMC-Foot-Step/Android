package com.footstep.dangbal.kotlin.src.main.Example

import com.footstep.dangbal.kotlin.src.main.Example.models.SignUpResponse
import com.footstep.dangbal.kotlin.src.main.Example.models.UserResponse



// Service에서 API 요청에 대한 응답 값을 Fragment에 전달하기 위한 "중간 인터페이스"
// Service에서 Fragment를 참조받기 위한 파일
interface HomeFragmentInterface {

    fun onGetUserSuccess(response: UserResponse)

    fun onGetUserFailure(message: String)

    fun onPostSignUpSuccess(response: SignUpResponse)

    fun onPostSignUpFailure(message: String)
}