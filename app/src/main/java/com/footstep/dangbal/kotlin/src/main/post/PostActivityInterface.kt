package com.footstep.dangbal.kotlin.src.main.post

import com.footstep.dangbal.kotlin.src.main.post.models.PostResponse

interface PostActivityInterface {
    // 발자취 정보 post api
    // 요청 성공
    suspend fun onPostPostInfoSuccess(response: PostResponse)
    // 요청 실패
    suspend fun onPostPostInfoFailure(message: String)
}