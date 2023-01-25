package com.softsquared.template.kotlin.src.main.gallery

import android.util.Log
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.softsquared.template.kotlin.src.main.gallery.models.PostListResponse
import com.softsquared.template.kotlin.src.main.gallery.models_sample.ResultFeetStepList
import com.softsquared.template.kotlin.src.main.gallery.models_sample.SectionModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// 메인 화면의 이벤트에 따른 API 요청을 보내는 "서비스 클래스"
/*
    백엔드 API를 엮기 전인 초기 프론트 리소스가지고 작업할 땐,
    서비스에서 더미 조회데이터를 만들어서 메인화면에 전달한다.

    백엔드 API 엮을 때는
    서비스에서 조회 API 요청을 보낸다. (Retrofit 객체 생성)
 */

class GalleryService (val galleryFragmentInterface: GalleryFragmentInterface) {

    // 전역변수 - 날짜별 카테고리 게시글 리스트 저장
    private val mSectionFeetStepList = ArrayList<ResultFeetStepList>()


    /*
        To Do 1. 발자취 리스트 조회 API의
        응답 더미데이터 생성
     */
    fun GetPostList() {


        /*
            To Do 1. 갤러리 발자취 조회 API 연결 테스팅
         */

        // ApplicationClass에서 선언해둔 SharedPreferences에 Jwt 토큰 값 임시로 저장
        ApplicationClass.sSharedPreferences.edit().putString(X_ACCESS_TOKEN, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImZvb3RzdGVwQG5hdmVyLmNvbSIsImlhdCI6MTY3NDY2MDA5MSwiZXhwIjoxNjc0OTYyNDkxfQ.W7MNMFI43SPbcw5pLhpbsuic0_nCDRcqHKPgEipV9ko").apply()

        val galleryRetrofitInterFace = ApplicationClass.sRetrofit.create(GalleryRetrofitInterface::class.java)
        galleryRetrofitInterFace.getGalleryPostList().enqueue(object : Callback<PostListResponse>{
            override fun onResponse(call: Call<PostListResponse>, response: Response<PostListResponse>) {

                // 요청 객체 예외처리
                if(response.body() != null) {
                    galleryFragmentInterface.onGetGalleryPostListSuccess(response.body() as PostListResponse)
                }
                else{
                    Log.d("요청 객체 받기실패", response.body().toString())
                }
            }

            override fun onFailure(call: Call<PostListResponse>, t: Throwable) {
                galleryFragmentInterface.onGetPostListFailure(t.message?: "통신 오류")
            }

        })













        // 발자취 리스트 응답 더미데이터 객체 생성
        // val postListResponse: FeetStepListResponse
        /*
            To Do 1. 발자취 리스트 조회 API의 응답 더미 데이터 생성
            1. 포스트 날짜
            2. 발자취 사진
            3. 발자취 제목
            4. 발자취 좋아요 갯수
            5. 발자취 장소(위치)
            6. 동일 날짜에 속한 발자취 게시글 갯수
            7. 발자취 좋아요 유무에 대한 상태값
         */

        // 발자취 게시글 리스트 Data객체 생성 - API 응답 데이터
        val resultFeetStepList = ArrayList<ResultFeetStepList>()
        resultFeetStepList.apply {
            add(ResultFeetStepList(
                "5월 31일",
                R.drawable.sample_post_img_1_1,
                "애니메이션 Car 모티브 카페",
                1,
                "당진 1950 카페",
                3,
            true))

            add(ResultFeetStepList(
                "5월 31일",
                R.drawable.sample_post_img_1_2,
                "만화속 카페..",
                1,
                "당진 1950 카페",
                3,
                true))

            add(ResultFeetStepList(
                "5월 31일",
                R.drawable.sample_post_img_1_3,
                "갬성 넘치는 철도길 뷰",
                1,
                "당진 1950 카페",
                3,
                true))




            add(ResultFeetStepList(
                "5월 3일",
                R.drawable.sample_post_img_2_1,
                "독고리에서 칼국수",
                0,
                "독곶리 해변",
                2,
                false))
            add(ResultFeetStepList(
                "5월 3일",
                R.drawable.sample_post_img_2_2,
                "독곶리의 므찐 일몰",
                0,
                "독곶리 해변",
                2,
                false))



            add(ResultFeetStepList(
                "5월 1일",
                R.drawable.sample_post_img_2_2,
                "독곶리의 므찐 일몰",
                0,
                "독곶리 해변",
                3,
                false))

            add(ResultFeetStepList(
                "5월 1일",
                R.drawable.sample_post_img_2_2,
                "독곶리의 므찐 일몰",
                0,
                "독곶리 해변",
                3,
                false))

            add(ResultFeetStepList(
                "5월 1일",
                R.drawable.sample_post_img_2_2,
                "독곶리의 므찐 일몰",
                0,
                "독곶리 해변",
                3,
                false))
        }



        /*
            날짜별 카테고리로 게시글 그룹화 - 데이터 전처리?
         */
        val daySectionFeetStepList = ArrayList<SectionModel>()

//        var day_cnt: Int
//        day_cnt = resultFeetStepList[0].post_cnt

            // 날짜별 카테고리 게시글 리스트 저장
//        val sectionFeetStepList = ArrayList<ResultFeetStepList>()

//        for (i: Int in 1..resultFeetStepList.size) {
//            if (day_cnt == 0) {
//                Log.d("데이터 객체가 잘 저장되었는지 확인", "data list cnt = " + sectionFeetStepList.size)
//                daySectionFeetStepList.add(
//                    SectionModel(
//                        resultFeetStepList[i - 2].day,
//                        sectionFeetStepList
//                    )
//                )
//                day_cnt = resultFeetStepList[i - 1].post_cnt
//
//                // 동일한 객체를 초기화해서, 저장된 값도 변경됨 - 얕은 복사
//                sectionFeetStepList.clear()
//
//                sectionFeetStepList.add(
//                    resultFeetStepList[i - 1]
//                )
//
//            } else {
//
//                sectionFeetStepList.add(
//                    resultFeetStepList[i - 1]
//                )
//
//                day_cnt--
//            }
//        }
//
//        // 마지막 날짜별 카테고리 게시글 리스트 저장
//        daySectionFeetStepList.add(
//            SectionModel(
//                resultFeetStepList[resultFeetStepList.size - 1].day,
//                sectionFeetStepList
//            )
//        )

        var idx: Int = 0


        // 백엔드 API에서 날짜별 카테고리 갯수가 몇개인지 받기
        // 숫자 2를 API에서 받아오기
        for(i: Int in 1..3){


            // 카테고리 별 게시글 리스트 데이터 - ArrayList 객체 생성
            val sectionFeetStepList = ArrayList<ResultFeetStepList>()

            var z: Int = resultFeetStepList[idx].post_cnt
            while(z > 0){

                sectionFeetStepList.add(
                    resultFeetStepList[idx]
                )
                idx ++
                z--
            }

            daySectionFeetStepList.add(
                SectionModel(
                    resultFeetStepList[idx - 1].day,
                    sectionFeetStepList
                )
            )

        }


//        Log.d("더미데이터 리스트 사이즈 체크", "data_size = " + daySectionFeetStepList.size)
//        Log.d("더미데이터 카테고리 리스트 체크", "data_size = " + daySectionFeetStepList[0].day_post_list.size)
//        Log.d("더미데이터 카테고리 리스트 체크_2", "data_size = " + daySectionFeetStepList[1].day_post_list.size)
//
//            for(z: Int in 1..3){
//                Log.d("더미데이터 체크", "data_Title" + daySectionFeetStepList[0].day_post_list[z-1].title)
//            }
//            for(y: Int in 1..2){
//                Log.d("더미데이터 체크", "data_Title" + daySectionFeetStepList[1].day_post_list[y-1].title)
//
//            }
//            for(x: Int in 1..3){
//                Log.d("더미데이터 체크", "data_Title" + daySectionFeetStepList[2].day_post_list[x-1].title)
//
//             }



        // GalleryFragemnt로 발자취 게시글 데이터 전달 - 날짜별 카테고리 게시글 리스트 Data
        galleryFragmentInterface.onGetPostListSuccess(daySectionFeetStepList)








    }

}