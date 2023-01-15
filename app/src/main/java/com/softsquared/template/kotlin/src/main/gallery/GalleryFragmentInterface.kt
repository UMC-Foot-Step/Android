package com.softsquared.template.kotlin.src.main.gallery

import com.softsquared.template.kotlin.src.main.gallery.models.FeetStepListResponse
import com.softsquared.template.kotlin.src.main.gallery.models.SectionModel

// Service에서 API 요청에 대한 응답 값을 Fragment에 전달하기 위한 "중간 인터페이스"
// Service에서 Fragment를 참조받기 위한 파일
interface GalleryFragmentInterface {


    fun onGetPostListSuccess(response: List<SectionModel>)

    fun onGetPostListFailure(message: String)

    fun changeGalleryInfoActivity()
}