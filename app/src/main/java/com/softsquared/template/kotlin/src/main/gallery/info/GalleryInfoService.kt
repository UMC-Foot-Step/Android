package com.softsquared.template.kotlin.src.main.gallery.info

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.Example.HomeRetrofitInterface
import com.softsquared.template.kotlin.src.main.Example.models.SignUpResponse
import com.softsquared.template.kotlin.src.main.gallery.GalleryRetrofitInterface
import com.softsquared.template.kotlin.src.main.gallery.info.models.PostInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryInfoService (val galleryInfoActivityInterface: GalleryInfoActivityInterface) {


    /*
        To Later 1. 발자취 상세 정보 조회 API 엮기
     */
    fun getPostInfo(posting_id: Int){

        val GalleryRetrofitInterface = ApplicationClass.sRetrofit.create(GalleryRetrofitInterface::class.java)
        GalleryRetrofitInterface.getGalleryPostInfo(posting_id).enqueue(object :
            Callback<PostInfoResponse> {
            override fun onResponse(call: Call<PostInfoResponse>, response: Response<PostInfoResponse>) {
                galleryInfoActivityInterface.onGetPostInfoSuccess(response.body() as PostInfoResponse)
            }

            override fun onFailure(call: Call<PostInfoResponse>, t: Throwable) {
                galleryInfoActivityInterface.onGetPostInfoFailure(t.message ?: "통신 오류")
            }
        })
        /*
            API 엮을 때,
            특정 발자취 정보 상세 조회 API 요청보내고,
            응답 데이터 받기
        */

        /*
            받아진 응답 데이터를 Activity에 전달해서,
            Acitivity View 구현
         */
    }

}