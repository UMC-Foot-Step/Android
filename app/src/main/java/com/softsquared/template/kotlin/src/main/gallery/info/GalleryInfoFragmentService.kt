package com.softsquared.template.kotlin.src.main.gallery.info


import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.src.main.Example.HomeRetrofitInterface
import com.softsquared.template.kotlin.src.main.Example.models.SignUpResponse
import com.softsquared.template.kotlin.src.main.gallery.GalleryRetrofitInterface
import com.softsquared.template.kotlin.src.main.gallery.info.models.PostInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create



class GalleryInfoFragmentService(val galleryInfoFragmentInterface: GalleryInfoFragmentInterface) {

    /*
    To Do 3. 게시글 댓글 삭제 API
    */
    fun deletePostComment(comment_id: Int) {
        val GalleryRetrofitInterface =
            ApplicationClass.sRetrofit.create(GalleryRetrofitInterface::class.java)
        GalleryRetrofitInterface.deletePostComment(comment_id).enqueue(object :
            Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                galleryInfoFragmentInterface.onDeletePostCommentSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                galleryInfoFragmentInterface.onDeletePostCommentFailure(t.message ?: "통신 오류")
            }
        })
    }

}