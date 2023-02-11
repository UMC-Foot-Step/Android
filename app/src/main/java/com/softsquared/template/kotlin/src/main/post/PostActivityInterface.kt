package com.softsquared.template.kotlin.src.main.post

import com.softsquared.template.kotlin.src.main.post.models.PostResponse

interface PostActivityInterface {
    // 발자취 정보 post api
    // 요청 성공
    fun onPostPostInfoSuccess(response: PostResponse)
    // 요청 실패
    fun onPostPostInfoFailure(message: String)
}