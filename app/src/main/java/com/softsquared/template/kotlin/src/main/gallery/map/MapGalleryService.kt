package com.softsquared.template.kotlin.src.main.gallery.map

import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.src.main.gallery.models.ResultFeetStepList
import com.softsquared.template.kotlin.src.main.gallery.models.SectionModel

class MapGalleryService(val mapGalleryActivityInterface: MapGalleryActivityInterface) {

    /*
    To Do 2. 동일 위치 정보를 API 요청 값으로 요청 후
    동일 위치에 따른 발자취 게시글 리스트 응답 데이터 받음

    받고, MapGalleryActivity로 응답 데이터 넘겨줌
    */
    fun onGetPostListByPosition(){

        // 응답 데이터 구성
        // 발자취 게시글 리스트 Data객체 생성 - API 응답 데이터
        val resultFeetStepList = ArrayList<ResultFeetStepList>()
        resultFeetStepList.apply {
            add(
                ResultFeetStepList(
                "5월 31일",
                R.drawable.sample_post_img_1_1,
                "애니메이션 Car 모티브 카페",
                1,
                "당진 1950 카페",
                5,
                true)
            )

            add(
                ResultFeetStepList(
                "5월 31일",
                R.drawable.sample_post_img_1_2,
                "만화속 카페..",
                1,
                "당진 1950 카페",
                5,
                true)
            )

            add(
                ResultFeetStepList(
                "5월 31일",
                R.drawable.sample_post_img_1_3,
                "갬성 넘치는 철도길 뷰",
                1,
                "당진 1950 카페",
                5,
                true)
            )




            add(
                ResultFeetStepList(
                "5월 31일",
                R.drawable.sample_post_img_2_1,
                "여긴 어딘가?",
                0,
                "당진 1950 카페",
                5,
                false)
            )
            add(
                ResultFeetStepList(
                "5월 31일",
                R.drawable.sample_post_img_2_2,
                "우왓 대박",
                0,
                "당진 1950 카페",
                5,
                false)
            )



            add(
                ResultFeetStepList(
                "5월 1일",
                R.drawable.sample_post_img_2_2,
                "프론트 개발 완룡~",
                0,
                "당진 1950 카페",
                3,
                false)
            )

            add(
                ResultFeetStepList(
                "5월 1일",
                R.drawable.sample_post_img_2_2,
                "일몰이지롱",
                0,
                "당진 1950 카페",
                3,
                false)
            )

            add(
                ResultFeetStepList(
                "5월 1일",
                R.drawable.sample_post_img_2_2,
                "일몰",
                0,
                "당진 1950 카페",
                3,
                false)
            )
        }

        /*
    날짜별 카테고리로 게시글 그룹화 - 데이터 전처리?
 */
        val daySectionFeetStepList = ArrayList<SectionModel>()
        var idx: Int = 0

        // 백엔드 API에서 날짜별 카테고리 갯수가 몇개인지 받기
        // 숫자 2를 API에서 받아오기
        for(i: Int in 1..2){


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


        // API 응답 데이터, MapGalleryActivity로 전달
        mapGalleryActivityInterface.onGetPostListByPositionSuccess(daySectionFeetStepList, mapGalleryActivityInterface)
    }

}