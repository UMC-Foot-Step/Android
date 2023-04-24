package com.footstep.dangbal.kotlin.src.main.postupdate

import com.footstep.dangbal.kotlin.src.main.postupdate.models.PostEditResponse
import com.footstep.dangbal.kotlin.src.main.postupdate.models.PostUpdateResponse

interface PostUpdateActivityInterface {
    // 발자취 정보 get api
    // 요청 성공
    fun onGetPostUpdateInfoSuccess(response: PostUpdateResponse)
    // 요청 실패
    fun onGetPostUpdateInfoFailure(message: String)

    // 발자취 정호 post api
    // post 성공
    fun onPostPostUpdateInfoSuccess(response: PostEditResponse)
    // post 실패
    fun onPostPostUpdateInfoFailure(message: String)
}