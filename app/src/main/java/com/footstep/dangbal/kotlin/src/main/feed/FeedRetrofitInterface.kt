package com.footstep.dangbal.kotlin.src.main.feed

import com.footstep.dangbal.kotlin.config.BaseResponse
import com.footstep.dangbal.kotlin.src.main.feed.models.FeedListResponse
import com.footstep.dangbal.kotlin.src.main.feed.models.ReportResponse
import com.footstep.dangbal.kotlin.src.main.feed.models.createReportDto
import com.footstep.dangbal.kotlin.src.main.gallery.models.PostListResponse
import retrofit2.Call
import retrofit2.http.*

interface FeedRetrofitInterface {

    /*
        To Do 1. 타 유저 피드 리스트 조회 API
     */
    @GET("/footstep/feed")
    fun getFeedList() : Call<FeedListResponse>


    /*
        To Do 2. 피드 - 특정 피드 상세 조회 API
     */
    // GalleryRetrofitInterface에서 사용하여 피드 레트로핏에는 생략구현

    /*
        To Do 3. 피드 - 특정 유저 피드 리스트 조회 API
    */
    @GET("footstep/feed/{user-id}")
    fun getSpecificFeedList(@Path("user-id") user_id: Int) : Call<PostListResponse>


    /*
        To Do 4. 발자취 신고
     */
    @POST("footstep/{posting-id}/posting-report")
    fun ReportPost(@Body createReportDto: createReportDto,
                   @Path("posting-id") posting_id: Int) : Call<ReportResponse>

    /*
    여기서 부터는 갤러리 기능이 안되서 중복해서 여기에도 만든 api
        To Do 5. 유저 신고



    @POST("{users-id}/users-report")
    fun reportUser(@Path("users-id") users_id: Int,
                   @Body createReportDto: createReportDto) : Call<ReportResponse>



    /*
        To Do 6. GalleryInfo - 게시글 댓글 삭제 API 호출
     */
    @PATCH("footstep/{comment_id}")
    fun deletePostComment(@Path("comment_id") comment_id: Int) : Call<BaseResponse>
*/

}