package com.softsquared.template.kotlin.src.main.gallery.models_sample

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse


//1. 포스트 날짜
//2. 발자취 사진
//3. 발자취 제목
//4. 발자취 좋아요 갯수
//5. 발자취 장소(위치)
//6. 동일 날짜에 속한 발자취 게시글 갯수
//7. 발자취 좋아요 유무에 대한 상태값

/*
    GalleryFragment - 발자취 리스트 조회 데이터 객체
 */
data class ResultFeetStepList(
    // 베이스 리스폰스를 상속 받았으므로, 아래 내용은 포함이 되었습니다.
    // @SerializedName("isSuccess") val isSuccess: Boolean,
    // @SerializedName("code") val code: Int,
    // @SerializedName("message") val message: String

    @SerializedName("day") val day: String,
    @SerializedName("img") val img: Int,
    @SerializedName("title") val title: String,
    @SerializedName("like_cnt") val like_cnt: Int,
    @SerializedName("position") val position: String,
    @SerializedName("post_cnt") val post_cnt: Int,
    @SerializedName("like_status") val like_status: Boolean

) : BaseResponse()
