package com.footstep.dangbal.kotlin.src.main.gallery

import com.footstep.dangbal.kotlin.src.main.gallery.models.PostListByDateResponse
import com.footstep.dangbal.kotlin.src.main.gallery.models.PostListResponse

// Service에서 API 요청에 대한 응답 값을 Fragment에 전달하기 위한 "중간 인터페이스"
// Service에서 Fragment를 참조받기 위한 파일
interface GalleryFragmentInterface {


//  fun onGetPostListSuccess(response: List<SectionModel>)

    fun onGetPostListFailure(message: String)

    fun changeGalleryInfoActivity(post_idx: Int)


    /*
        To DO 1. 서버 API 연결
     */
    fun onGetGalleryPostListSuccess(response: PostListResponse)

    // 테스팅 메소드
    /*
        동일 장소별 발자취 게시글 리스트 조회 Activity를 테스팅하기 위한 메소드
     */
    fun testChangeMapGalleryActivity()

    /*
        TO Do 2. 날짜별 특정날짜 게시글 리스트 조회 API 응답 메소드
     */
    fun onGetGalleryPostListByDateSuccess(response: PostListByDateResponse)

    fun onGetGalleryPostListByDateFailure(message: String)
}