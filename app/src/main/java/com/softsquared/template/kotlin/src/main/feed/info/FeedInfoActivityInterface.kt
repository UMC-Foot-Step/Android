package com.softsquared.template.kotlin.src.main.feed.info

import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.src.main.feed.models.ReportResponse
import com.softsquared.template.kotlin.src.main.gallery.info.models.PostInfoResponse

interface FeedInfoActivityInterface {

    /*
        발자취 상세 정보 조회 API
    */
    // 발자취 상세 정보 조회 API 요청 성공
    fun onGetPostInfoSuccess(response: PostInfoResponse)

    // 발자취 상세 정보 조회 API 요청 실패
    fun onGetPostInfoFailure(message: String)

    /*
        발자취 게시글 댓글 삭제 API
    */
    // 발자취 게시글 댓글 삭제 API 요청 성공
    fun onDeletePostCommentSuccess(response: BaseResponse)

    // 발자취 게시글 댓글 삭제 API 요청 실패
    fun onDeletePostCommentFailure(message: String)


    /*
        댓글 & 좋아요 클릭 API 요청 실패
     */
    fun onDeletePostFailure(message: String)

    // 댓글 - 댓글 신고하기
    // 댓글 신고하기 API 요청 성공
    fun onReportCommentSuccess(response: ReportResponse)
    // 댓글 신고하기 API 요청 실패
    fun onReportCommentFailure(message: String)
    
    
    // 댓글 - 유저 신고하기
    
    // 유저 신고하기 API 요청 성공
    
    // 유저 신고하기 API 요청 실패

    //게시글 신고하기
    fun onReportPostSuccess(response:ReportResponse)
    fun onReportPostFailure(message: String)


}