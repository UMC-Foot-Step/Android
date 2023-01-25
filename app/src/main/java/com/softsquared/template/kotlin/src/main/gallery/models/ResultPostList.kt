package com.softsquared.template.kotlin.src.main.gallery.models

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
data class ResultPostList(

    @SerializedName("postingListDto") val post_list: ArrayList<PostList>,
    @SerializedName("uploadDates") val upload_date: Int
)
