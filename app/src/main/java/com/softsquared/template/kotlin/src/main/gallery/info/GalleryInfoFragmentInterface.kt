package com.softsquared.template.kotlin.src.main.gallery.info

import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.src.main.feed.models.ReportResponse

interface GalleryInfoFragmentInterface {

    /*
    발자취 게시글 댓글 삭제 API
    */
    // 발자취 게시글 댓글 삭제 API 요청 성공
    fun onDeletePostCommentSuccess(response: BaseResponse)

    // 발자취 게시글 댓글 삭제 API 요청 실패
    fun onDeletePostCommentFailure(message: String)


    fun onReportCommentSuccess(response:ReportResponse)

    fun onReportCommentFailure(message: String)

}