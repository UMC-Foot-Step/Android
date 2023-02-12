package com.softsquared.template.kotlin.src.main.gallery

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentGallaryBinding
import com.softsquared.template.kotlin.src.main.gallery.info.GalleryInfoActivity
import com.softsquared.template.kotlin.src.main.gallery.map.MapGalleryActivity
import com.softsquared.template.kotlin.src.main.gallery.models.PostList
import com.softsquared.template.kotlin.src.main.gallery.models.PostListByDateResponse
import com.softsquared.template.kotlin.src.main.gallery.models.PostListResponse
import com.softsquared.template.kotlin.src.main.gallery.models.SectionModel
import java.time.LocalDate


class GalleryFragment :
    BaseFragment<FragmentGallaryBinding>(FragmentGallaryBinding::bind, R.layout.fragment_gallary),
    GalleryFragmentInterface {

    // 갤러리 - 실행 프래그먼트 체크
    private var run_id: Int = 0

    // 갤러리 - 프래그먼트 기능전환 체크
    /*
        1 -> 모아두기 실행
        2 -> 날짜별 실행
     */
    private var feature_check: Int = 1

    // 갤러리 확장기능 - 캘린더 다이얼로그
    private lateinit var calBottomSheet:View

    private var calYear = LocalDate.now().year
    private var calMonth = LocalDate.now().monthValue
    private var calDay = LocalDate.now().dayOfMonth

    // 갤러리 예외 뷰
    private lateinit var exceptionView: View

    // 갤러리 날짜별 특정날짜 저장
    private lateinit var date: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calBottomSheet = layoutInflater.inflate(R.layout.fragment_map_calendar, null)
        exceptionView = layoutInflater.inflate(R.layout.item_gallery_exception, null)

        /*
            To Do 9. 모아보기 Deafult 실행 - 텍스트 색 설정
        */
        // 모아두기 텍스트 Default - 색상 변경
        binding.galleryFeatureAllTxt.setTextColor(Color.parseColor("#ff7b31"))
        binding.galleryFeatureDateTxt.setTextColor(Color.parseColor("#000000"))




        // 프래그먼트 객체 실행
        run_id = 0
        // 모아두기 Default 지정
        feature_check = 1





        /*
            To Do 1. 갤러리 뷰 형태로 발자취 리스트 조회 구현
            1. 서비스한테 발자취 리스트 더미데이터 생성 후 응답 받는다.
            2. 응답 받은 더미데이터를 가지고 onGetPostListSuccess() 메소드 호출하여
            해당 프래그먼트의 Recycler View 형태로 구현한다.
         */
        GalleryService(this).GetPostList()
        Log.d("onViewCreated()", "실행됨")


        /*
            To Do 10. 날짜별 갤러리 게시글 조회
         */
        binding.galleryFeatureDateTxt.setOnClickListener {

            // 날짜별 텍스트 클릭 이벤트 - 색상 변경
            binding.galleryFeatureDateTxt.setTextColor(Color.parseColor("#ff7b31"))
            binding.galleryFeatureAllTxt.setTextColor(Color.parseColor("#000000"))


            /*
                To Do 11. 달력 다이얼로그 띄우기
            */
            val bottomSheetDialog = activity?.let { it1 -> BottomSheetDialog(it1) }

            val btnClose = calBottomSheet.findViewById<ImageButton>(R.id.dateBtnQuit)
            val btnDateCheck = calBottomSheet.findViewById<Button>(R.id.dateBtnCheck)
            val calView = calBottomSheet.findViewById<CalendarView>(R.id.calenderView)

            calView.maxDate = System.currentTimeMillis()

            // calendar bottomsheetdialog 설정
            if(calBottomSheet.parent!=null) {
                var p=calBottomSheet.parent as ViewGroup
                p.removeView(calBottomSheet)
            }
            if (bottomSheetDialog != null) {
                bottomSheetDialog.setContentView(calBottomSheet)
                bottomSheetDialog.show()
            }

            // 캘린더 다이얼로그 취소 버튼 이벤트
            btnClose.setOnClickListener {

                // 캘린더 다이얼로그 제거 & 텍스트 업데이트
                if (bottomSheetDialog != null) {
                    bottomSheetDialog.dismiss()
                    // 날짜별 기능 취소 버튼 이벤트 - 텍스트 색상 변경
                    binding.galleryFeatureAllTxt.setTextColor(Color.parseColor("#ff7b31"))
                    binding.galleryFeatureDateTxt.setTextColor(Color.parseColor("#000000"))


                }
            }


            // 캘린더 내의 날짜 변경 이벤트
            calView.setOnDateChangeListener { calView, year, month, dayOfMonth ->
                calYear = year
                calMonth = month + 1
                calDay = dayOfMonth
            }

            // 캘린더 다이얼로그 확인 버튼 이벤트
            btnDateCheck.setOnClickListener{

                // 캘린더 다이얼로그 제거 & 특정 날짜별 게시글 API 호출
                if (bottomSheetDialog != null) {
                    bottomSheetDialog.dismiss()

                    /*
                        To Do 12. 캘린더 지정 Date로 특정 날짜별 게시글 조회 API 호출
                    */

                    // 갤러리 - 날짜별 기능 실행상태
                    feature_check = 2

                    // 캘린더 날짜 데이터 가져오기
                    val date = "$calYear-$calMonth-$calDay"
                    this.date = date
                    // API 호출
                    GalleryService(this).getPostListByDate(date)

                }


            }

        }

        /*
            To Do 13. 모아보기 - 갤러리 게시글 리스트 조회
        */
        binding.galleryFeatureAllTxt.setOnClickListener {

            // 갤러리 - 모아두기 실행상태
            feature_check = 1

            // 모아두기 텍스트 클릭 이벤트 - 색상 변경
            binding.galleryFeatureAllTxt.setTextColor(Color.parseColor("#ff7b31"))
            binding.galleryFeatureDateTxt.setTextColor(Color.parseColor("#000000"))

            // 모아두기 - 갤러리 게시글 리스트 조회 API 호출
            GalleryService(this).GetPostList()
        }

        }



    /*
        To Do 8. 갤러리 프래그먼트 재시작 시 실행 생명주기 메소드
     */
    override fun onStart() {
        super.onStart()
        Log.d("onStart()", "실행됨")



        // 게시글 상세 조회 뷰에서 종료하고 넘어올 때 예외작업 {추가 필요}
        // API 호출 최적화 (코드 리팩토링)

        // 모아두기 실행 상태
        if(run_id != 0 && feature_check == 1) {
            /*
            To Do 9. 갤러리 발자취 게시글 리스트 조회 API 재실행
            */
            Log.d("onStart()", "API 실행됨")
            GalleryService(this).GetPostList()
        }
        // 날짜별 실행 상태
        else if(run_id != 0 && feature_check == 2) {
            GalleryService(this).getPostListByDate(date)
        }

        run_id = 1
    }



    /*
    To Do 2. 갤러리 뷰 형태로 발자취 리스트 조회 구현

    1. 받은 응답 더미 데이터를 가지고 RecyclerView로 구현한다.
    */
//    override fun onGetPostListSuccess(response: List<SectionModel>) {
//        setupRecyclerView(response, this)
//    }

    
    /*
        To Do Later
        API 엮을 때 해당 메소드 구현하여
        API 요청 실패에 대한 예외처리
     */
    override fun onGetPostListFailure(response: String) {
        showCustomToast(response)
        Log.d("API 연결 에러", response)

    }




    /*
        To DO 3. RecyclerView 구현
     */
    private fun setupRecyclerView(response: List<SectionModel>, galleryFragmentInterface: GalleryFragmentInterface){
        binding.galleryRvFeetstepList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
//            (layoutManager as LinearLayoutManager).setStackFromEnd(true)
            adapter = GalleryFragmentAdater(response, galleryFragmentInterface)
        }
    }



    /*
        To DO 4. 프래그먼트 -> GalleryInfoActivity로 전환
        리사이클러 뷰의 특정 Item 클릭 후
        특정 Item 조회 Activity
     */
    override fun changeGalleryInfoActivity(post_idx: Int){
        val intent = Intent(activity, GalleryInfoActivity::class.java)
//        val bundle = Bundle()
//        bundle.putSerializable("feetStepInfoResponse", feetStepInfoResponse)
        intent.putExtra("posting-id", post_idx)
        startActivity(intent)
    }




    /*
        특정 장소 발자취 게시글 리스트 조회 액티비티 전환 메소드
     */
    override fun testChangeMapGalleryActivity(){
        val intent = Intent(activity, MapGalleryActivity::class.java)
        startActivity(intent)
    }

    /*
        To DO 5. 갤러리 게시글 리스트 조회 API 연결
    */
    override fun onGetGalleryPostListSuccess(response: PostListResponse) {


        /*
            To Do 6. 게시글 예외처리 - 게시글 존재하지 않을 상황 예외처리
        */
        val daySectionFeetStepList = ArrayList<SectionModel>()

        if(response.isSuccess == false){

            setupRecyclerView(daySectionFeetStepList, this)
        }
        else {

            var idx: Int = 0

            // 날짜별 카테고리  갯수만큼 반복
            for(i: Int in 1..response.result.upload_date){

                // 카테고리 별 게시글 리스트 데이터 - ArrayList 객체 생성
                val sectionFeetStepList = ArrayList<PostList>()

                var z: Int = response.result.post_list[idx].posting_cnt
                while(z > 0){

                    sectionFeetStepList.add(
                        response.result.post_list[idx]
                    )
                    idx ++
                    z--
                }

                daySectionFeetStepList.add(
                    SectionModel(
                        response.result.post_list[idx - 1].recordDate,
                        sectionFeetStepList
                    )
                )

            }

            setupRecyclerView(daySectionFeetStepList, this)

        }

    }




    /*
        To Do 14. 갤러리 - 날짜별 특정날짜 게시글 리스트 조회 API 응답 메소드
     */
    override fun onGetGalleryPostListByDateSuccess(response: PostListByDateResponse) {

        // 발자취 데이터 객체 - 리사이클러 아이템 객체
        val daySectionFeetStepList = ArrayList<SectionModel>()

        // 뷰 업데이트
        if(response.isSuccess == false){
//            response.message?.let { showCustomToast(it) }

            /*
                To Do 6. 게시글 예외처리 - 게시글이 존재하지 않을 때의 예외 뷰 {구현 필요}
            */
            setupRecyclerView(daySectionFeetStepList, this)
        }
        else {


            var idx: Int = 0

            // 카테고리 별 게시글 리스트 데이터 - ArrayList 객체 생성
            val sectionFeetStepList = ArrayList<PostList>()

            var z: Int = response.result.post_list[0].posting_cnt
            while (z > 0) {

                sectionFeetStepList.add(
                    response.result.post_list[idx]
                )
                z--
                idx++
            }

            daySectionFeetStepList.add(
                SectionModel(
                    response.result.post_list[idx - 1].recordDate,
                    sectionFeetStepList
                )
            )

            setupRecyclerView(daySectionFeetStepList, this)
        }

    }

    override fun onGetGalleryPostListByDateFailure(message: String) {
        showCustomToast(message)
        Log.d("API 응답 에러", message)
    }


}

