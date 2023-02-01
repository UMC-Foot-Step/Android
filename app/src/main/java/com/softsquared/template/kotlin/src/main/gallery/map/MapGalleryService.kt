package com.softsquared.template.kotlin.src.main.gallery.map

import android.util.Log
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.gallery.GalleryRetrofitInterface
import com.softsquared.template.kotlin.src.main.gallery.models.PostListResponse
import com.softsquared.template.kotlin.src.main.gallery.models_sample.ResultFeetStepList
import com.softsquared.template.kotlin.src.main.gallery.models_sample.SectionModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapGalleryService(val mapGalleryActivityInterface: MapGalleryActivityInterface) {

    /*
    To Do 2. 동일 위치 정보를 API 요청 값으로 요청 후
    동일 위치에 따른 발자취 게시글 리스트 응답 데이터 받음

    받고, MapGalleryActivity로 응답 데이터 넘겨줌
    */
    fun onGetPostListByPosition(placeId: Int){

        val galleryRetrofitInterFace =
            ApplicationClass.sRetrofit.create(GalleryRetrofitInterface::class.java)
        galleryRetrofitInterFace.getPostListByPosition(placeId).enqueue(object : Callback<PostListResponse> {

            // API 요청 에러 & 요청 성공 시 실행
            override fun onResponse(
                call: Call<PostListResponse>,
                response: Response<PostListResponse>
            ) {

                // 요청 객체 예외처리
                if (response.body() != null) {
                    mapGalleryActivityInterface.onGetPostListByPositionSuccess(response.body() as PostListResponse, mapGalleryActivityInterface)
                } else {
                    Log.d("요청 객체 받기실패", response.body().toString())
                }
            }


            // API 응답에러가 발생 시 실행
            override fun onFailure(call: Call<PostListResponse>, t: Throwable) {
                mapGalleryActivityInterface.onGetPostListByPositionFailure(t.message ?: "통신 오류")
            }

        })

        // 응답 데이터 구성
        // 발자취 게시글 리스트 Data객체 생성 - API 응답 데이터
//        val resultFeetStepList = ArrayList<ResultFeetStepList>()
//        resultFeetStepList.apply {
//            add(
//                ResultFeetStepList(
//                "5월 31일",
//                R.drawable.sample_post_img_1_1,
//                "애니메이션 Car 모티브 카페",
//                1,
//                "당진 1950 카페",
//                5,
//                true)
//            )
//
//            add(
//                ResultFeetStepList(
//                "5월 31일",
//                R.drawable.sample_post_img_1_2,
//                "만화속 카페..",
//                1,
//                "당진 1950 카페",
//                5,
//                true)
//            )
//
//            add(
//                ResultFeetStepList(
//                "5월 31일",
//                R.drawable.sample_post_img_1_3,
//                "갬성 넘치는 철도길 뷰",
//                1,
//                "당진 1950 카페",
//                5,
//                true)
//            )
//
//
//
//
//            add(
//                ResultFeetStepList(
//                "5월 31일",
//                R.drawable.sample_post_img_2_1,
//                "여긴 어딘가?",
//                0,
//                "당진 1950 카페",
//                5,
//                false)
//            )
//            add(
//                ResultFeetStepList(
//                "5월 31일",
//                R.drawable.sample_post_img_2_2,
//                "우왓 대박",
//                0,
//                "당진 1950 카페",
//                5,
//                false)
//            )
//
//
//
//            add(
//                ResultFeetStepList(
//                "5월 1일",
//                R.drawable.sample_post_img_2_2,
//                "프론트 개발 완룡~",
//                0,
//                "당진 1950 카페",
//                3,
//                false)
//            )
//
//            add(
//                ResultFeetStepList(
//                "5월 1일",
//                R.drawable.sample_post_img_2_2,
//                "일몰이지롱",
//                0,
//                "당진 1950 카페",
//                3,
//                false)
//            )
//
//            add(
//                ResultFeetStepList(
//                "5월 1일",
//                R.drawable.sample_post_img_2_2,
//                "일몰",
//                0,
//                "당진 1950 카페",
//                3,
//                false)
//            )
//        }


//        val daySectionFeetStepList = ArrayList<SectionModel>()
//        var idx: Int = 0
//
//        // 백엔드 API에서 날짜별 카테고리 갯수가 몇개인지 받기
//        // 숫자 2를 API에서 받아오기
//        for(i: Int in 1..2){
//
//
//            // 카테고리 별 게시글 리스트 데이터 - ArrayList 객체 생성
//            val sectionFeetStepList = ArrayList<ResultFeetStepList>()
//
//            var z: Int = resultFeetStepList[idx].post_cnt
//            while(z > 0){
//
//                sectionFeetStepList.add(
//                    resultFeetStepList[idx]
//                )
//                idx ++
//                z--
//            }
//
//            daySectionFeetStepList.add(
//                SectionModel(
//                    resultFeetStepList[idx - 1].day,
//                    sectionFeetStepList
//                )
//            )
//
//        }
//
//
//        // API 응답 데이터, MapGalleryActivity로 전달
//        mapGalleryActivityInterface.onGetPostListByPositionSuccess(daySectionFeetStepList, mapGalleryActivityInterface)
    }

}