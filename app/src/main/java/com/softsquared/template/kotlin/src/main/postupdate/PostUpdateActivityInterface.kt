package com.softsquared.template.kotlin.src.main.postupdate

import com.softsquared.template.kotlin.src.main.postupdate.models.PostUpdateResponse

interface PostUpdateActivityInterface {
    // 발자취 정보 get api
    // 요청 성공
    fun onGetPostUpdateInfoSuccess(response: PostUpdateResponse)
    // 요청 실패
    fun onGetPostUpdateInfoFailure(message: String)
}