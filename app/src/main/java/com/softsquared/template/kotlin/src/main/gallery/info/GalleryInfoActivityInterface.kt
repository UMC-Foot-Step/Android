package com.softsquared.template.kotlin.src.main.gallery.info

import com.softsquared.template.kotlin.src.main.gallery.info.models.PostInfoResponse
interface GalleryInfoActivityInterface {

    // 발자취 상세 정보 조회 API 요청 성공
    fun onGetPostInfoSuccess(response: PostInfoResponse)

    // 발자취 상세 정보 조회 API 요청 실패
    fun onGetPostInfoFailure(message: String)

}