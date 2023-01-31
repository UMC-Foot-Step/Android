package com.softsquared.template.kotlin.src.main.gallery.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.src.main.gallery.models_sample.ResultFeetStepList


/*
    발자취 리스트 조회 API 통신 데이터 객체
 */
data class PostListResponse(
    // 베이스 리스폰스를 상속 받았으므로, 아래 내용은 포함이 되었습니다.
    // @SerializedName("isSuccess") val isSuccess: Boolean,
    // @SerializedName("code") val code: Int,
    // @SerializedName("message") val message: String,
    @SerializedName("result") val result: ResultPostList,
) : BaseResponse()