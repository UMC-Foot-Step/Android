package com.softsquared.template.kotlin.src.main.gallery

import com.softsquared.template.kotlin.src.main.gallery.models.FeetStepListResponse
import com.softsquared.template.kotlin.src.main.gallery.models.ResultFeetStepList


// 메인 화면의 이벤트에 따른 API 요청을 보내는 "서비스 클래스"
/*
    백엔드 API를 엮기 전인 초기 프론트 리소스가지고 작업할 땐,
    서비스에서 더미 조회데이터를 만들어서 메인화면에 전달한다.

    백엔드 API 엮을 때는
    서비스에서 조회 API 요청을 보낸다. (Retrofit 객체 생성)
 */

class GalleryService {


    /*
        To Do 1. 발자취 리스트 조회 API의
        응답 더미데이터 생성
     */
    fun GetPostList() {

        // 발자취 리스트 응답 더미데이터 객체
        val postListResponse: FeetStepListResponse

    }
}