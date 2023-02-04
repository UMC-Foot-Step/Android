package com.softsquared.template.kotlin.src.main.map

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentMapBinding
import com.softsquared.template.kotlin.src.main.gallery.map.MapGalleryActivity
import com.softsquared.template.kotlin.src.main.map.area.AreaActivity
import com.softsquared.template.kotlin.src.main.map.model.AllResponse
import com.softsquared.template.kotlin.src.main.map.model.PopupResponse
import com.softsquared.template.kotlin.src.main.map.model.SpecificFstResponse
import com.softsquared.template.kotlin.src.main.map.search.SearchActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.time.LocalDate
import kotlin.properties.Delegates

class MapFragment :
    BaseFragment<FragmentMapBinding>(FragmentMapBinding::bind, R.layout.fragment_map),
    OnMapReadyCallback,MapFragmentInterface {
    // datepicker
    private val today = LocalDate.now()
    private var tvYear = today.year
    private var tvMonth = today.monthValue
    private var tvDay = today.dayOfMonth

    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체
    private lateinit var naverMap: NaverMap
    var firstRun=false

    var marker_info_hashMap=HashMap<Int, ResultPopupListMap>()
    var marker_hashMap=HashMap<Int,Marker>()
    //private val markerList:ArrayList<Marker> = ArrayList()
    //private val latlngList=Array<Array<Double>>(3){Array<Double>(3){0.0} }
    //private val latlngList=Array(4){DoubleArray(2){0.0} }
    val latlngList=arrayOf(arrayOf(37.56661,126.97839), arrayOf(37.56590, 126.98223), arrayOf(37.56905, 126.97767), arrayOf(37.33986, 126.74655),arrayOf(37.55946, 126.97514),arrayOf(37.57174, 126.97644),
                    arrayOf(37.38167,128.66018),arrayOf(37.38763,128.67408),arrayOf(37.62885,128.67582),arrayOf(38.11586,128.46362),arrayOf(37.86820,127.74334),arrayOf(36.95689,129.37683),
                    arrayOf(36.06576,126.82136),arrayOf(36.01980,126.73644),arrayOf(36.30994,126.51339),arrayOf(36.31697,127.43115),
                    arrayOf(36.56874,128.77939),arrayOf(35.83425,129.21927),arrayOf(35.78965,128.99677),arrayOf(35.14209,128.68822),
                    arrayOf(34.74621,127.65659),arrayOf(34.69562,127.18420),arrayOf(35.40832,127.36980),arrayOf(35.86321,127.06523),
                    arrayOf(33.42795,126.68450))
    val locationList=arrayOf("서울특별시청","을지로입구역","청계광장","왕동","숭례문","광화문역",
                    "정선군청","정선역","용평리조트 스키장","설악산","강원대학교 춘천캠퍼스","울진종합운동장",
                    "달고개 모시마을","금강하굿둑 관광지","대천 해수욕장","한밭 종합운동장",
                    "안동문화 관광단지","첨성대","장육산","소죽도 공원",
                    "소호항","비봉공룡알 화석지","왕정동 행정복지센터","전주월드컵 골프장",
                    "제주 마방목지")
    val imageList=arrayOf(R.drawable.cat_dummy, R.drawable.im2_dummy, R.drawable.google_dummy, R.drawable.chimchakman)

    private lateinit var mContext: Context
    private lateinit var v1:View

    private lateinit var bottomSheet:View
    private lateinit var bottomSheet2:View


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("생명주기","맵 프래그먼트의 onViewCreated")
        setRetainInstance(true);
        /* Activity가 onDestroy되고 재생성될 때 Fragment도 같이 재생성된다. 하지만 setRetainInstance(true)를 설정하면
       onCreate, onDestory가 호출되지 않고 재사용된다.->현재는 mvvm패턴의 viewModel써서 하라는데 몰라서 저거 일단 둠
        */
        v1 = layoutInflater.inflate(R.layout.feetstep_info, binding.placeInfo, true)
        bottomSheet = layoutInflater.inflate(R.layout.fragment_map_time_setting, null)
        bottomSheet2 = layoutInflater.inflate(R.layout.fragment_map_calendar, null)

        binding.mapView?.onCreate(savedInstanceState)
        binding.mapView?.getMapAsync(this)

        //현재위치 기능
        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        initButtonView()
    }


    override fun onAttach(context: Context) {
        runBlocking {
            super.onAttach(context)
            Log.d("생명주기", "맵 프래그먼트의 onAttach")
            val job = launch {
                subRoutine()
            }
            job.join()

            // 메인 액티비티 context 획득
            mContext = context
        }
    }

    private fun initButtonView() {
        binding.btnMenu.setOnClickListener {
            if (!binding.btnMenu.isSelected) {
                binding.btnMenu.background =
                    ContextCompat.getDrawable(mContext, R.drawable.menu_btn_selected_background)

                binding.subBtnLayout.visibility = View.VISIBLE
                binding.btnMenu.isSelected = true
            } else {
                binding.btnMenu.background =
                    ContextCompat.getDrawable(mContext, R.drawable.menu_btn_background)

                binding.btnMenu.isSelected = false
                binding.subBtnLayout.visibility = View.GONE
                binding.btnTime.isChecked = false
                binding.btnLocationCheck.isChecked = false
            }
        }

        binding.btnTime.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(mContext)
            val btnClose = bottomSheet.findViewById<ImageButton>(R.id.periodBtnQuit)
            val btnCal1 = bottomSheet.findViewById<ImageButton>(R.id.calBtn1)
            val btnCal2 = bottomSheet.findViewById<ImageButton>(R.id.calBtn2)
            val btnR=bottomSheet.findViewById<RadioButton>(R.id.radioBtn)
            val btnR2=bottomSheet.findViewById<RadioButton>(R.id.radioBtn2)
            val btnR3=bottomSheet.findViewById<RadioButton>(R.id.radioBtn3)
            val btnR4=bottomSheet.findViewById<RadioButton>(R.id.radioBtn4)
            val btnR5=bottomSheet.findViewById<RadioButton>(R.id.radioBtn5)
            val date1=bottomSheet.findViewById<TextView>(R.id.dateText1)
            val date2=bottomSheet.findViewById<TextView>(R.id.dateText2)
            val btnChk=bottomSheet.findViewById<TextView>(R.id.periodBtnCheck)

            val dsl=bottomSheet.findViewById<ConstraintLayout>(R.id.direct_select_layout)
            lateinit var dateReturn1:String
            lateinit var dateReturn2:String

            // date bottomsheetdialog 설정
            if(bottomSheet.parent!=null) {
                var p=bottomSheet.parent as ViewGroup
                p.removeView(bottomSheet)
            }
            bottomSheetDialog.setContentView(bottomSheet)
            bottomSheetDialog.show()

            var date = "$tvYear / $tvMonth / $tvDay"
            date1.text=date
            date2.text=date

            btnClose.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            btnR.setOnClickListener {
                dsl.visibility=View.GONE
            }
            btnR2.setOnClickListener {
                dsl.visibility=View.GONE
            }
            btnR3.setOnClickListener {
                dsl.visibility=View.GONE
            }
            btnR4.setOnClickListener {
                dsl.visibility=View.GONE
            }
            btnR5.setOnClickListener {
                dsl.visibility=View.VISIBLE
            }

            btnCal1.setOnClickListener{
                dateReturn1=setCalBtnClickEnent(1)
            }
            btnCal2.setOnClickListener{
                dateReturn2=setCalBtnClickEnent(2)
            }
            btnChk.setOnClickListener {
                runBlocking {
                    val job=launch{
                        subRoutine3(dateReturn1,dateReturn2)
                    }
                    job.join()
                }
                bottomSheetDialog.dismiss()
            }
        }

        binding.btnLocationCheck.setOnClickListener {
            val intent= Intent(requireContext(), AreaActivity::class.java)
            startActivity(intent)
        }

        binding.btnSearch.setOnClickListener{
            val intent= Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }

        binding.btnPosition.setOnClickListener {
           /* if(firstRun) {
                runBlocking {
                    val job = launch {
                        subRoutine()
                    }
                    job.join()
                }
            }
            */
            naverMap.locationSource = locationSource
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
            if(!firstRun)
                firstRun=true
        }
    }
    private fun subRoutine3(start_date:String,end_date:String){
        //return
        MapService(this).tryGetMapFootStepSpecific(start_date,end_date)
    }
    fun setCalBtnClickEnent(btn_type:Int):String{
        //val bottomSheetDialog = BottomSheetDialog(mContext,R.style.calTheme_Custom)
        val bottomSheetDialog = BottomSheetDialog(mContext)

        val btnClose = bottomSheet2.findViewById<ImageButton>(R.id.dateBtnQuit)
        val btnDateCheck = bottomSheet2.findViewById<Button>(R.id.dateBtnCheck)
        val calView = bottomSheet2.findViewById<CalendarView>(R.id.calenderView)
        lateinit var dateReturn:String

        calView.maxDate = System.currentTimeMillis()

        // calendar bottomsheetdialog 설정
        if(bottomSheet2.parent!=null) {
            var p=bottomSheet2.parent as ViewGroup
            p.removeView(bottomSheet2)
        }
        bottomSheetDialog.setContentView(bottomSheet2)
        bottomSheetDialog.show()

        btnClose.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        calView.setOnDateChangeListener { calView, year, month, dayOfMonth ->
            tvYear = year
            tvMonth = month + 1
            tvDay = dayOfMonth
        }

        btnDateCheck.setOnClickListener{
            dateReturn=setData(tvYear, tvMonth, tvDay, btn_type)
            bottomSheetDialog.dismiss()
        }
        return dateReturn
    }

    private fun setData(
        year: Int,
        month: Int,
        day: Int,
        btn_type: Int):String{
        var year1 by Delegates.notNull<Int>()
        var month1 by Delegates.notNull<Int>()
        var day1 by Delegates.notNull<Int>()


        var date = "$year - $month - $day"

        if(btn_type==1) {
            year1=year
            month1=month
            day1=day
            bottomSheet.findViewById<TextView>(R.id.dateText1).text = date
        }
        else {
            //date2 = "$year / $month / $day"
            if(year1>year){
                Toast.makeText(mContext, "날짜 범위에 맞지 않습니다", Toast.LENGTH_SHORT).show()
            }
            else{
                if(month1>month){
                    Toast.makeText(mContext, "날짜 범위에 맞지 않습니다", Toast.LENGTH_SHORT).show()
                }
                else{
                    if(day1>day){
                        Toast.makeText(mContext, "날짜 범위에 맞지 않습니다", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        bottomSheet.findViewById<TextView>(R.id.dateText2).text = date
                    }
                }
            }

        }
        return date
    }

    override fun onStart() {
        super.onStart()
        Log.d("생명주기","맵 프래그먼트의 onStart()")

        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        Log.d("생명주기","맵 프래그먼트의 onResume()")

        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        Log.d("생명주기","맵 프래그먼트의 onPause()")

        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        Log.d("생명주기","맵 프래그먼트의 onStop()")

        binding.mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d("생명주기","맵 프래그먼트의 onLowMemory()")

        binding.mapView.onLowMemory()
    }

    private fun subRoutine(){//:HashMap<Int,Marker>{
        //return
        MapService(this).tryGetMapFootStepList()
    }

    override fun onMapReady(@NonNull naverMap: NaverMap) {
        Log.d("생명주기", "onMapReady")
        this.naverMap = naverMap

        Log.d("FootStepList", "해시마커 포문직전")

            //  withContext(Main) {
        CoroutineScope(Dispatchers.Main).launch {
            for ((key, value) in marker_hashMap) {//marker_hashMap) {
                Log.d("FootStepList", "해시마커 포문 안되니+ $key")
                value.map = naverMap
                value.setOnClickListener {
                    setMarkerClickEvent(key, value, value.tag as Boolean)
                    true
                }
            }
        }

        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

      /*  for ((i, value) in latlngList.withIndex()) {
            if (i < 4 || i > 5) {
                Marker().apply {
                    position = LatLng(value[0], value[1])
                    map = naverMap
                    icon = OverlayImage.fromResource(R.drawable.footstep_orange)
                    tag = false

                    setOnClickListener {
                            //val important = tag as Boolean
                        setDummyMarkerClickEvent(true, this, tag as Boolean, i)

                        true

                    }
                }
            }
       */
        //Log.d("FootStepList", "런블락킹 나감")

        naverMap.setOnMapClickListener{  _, coord ->
            binding.placeInfo.visibility=View.GONE
        }
    }


    fun setDummyMarkerClickEvent(
        marker_type:Boolean,
        marker: Marker,
        tag: Boolean,
        marker_num:Int
    ) {
        if(tag==false){
            displayDummyFeetStepInfoView(marker_type,marker_num)
            marker.tag = true
        }
        else{
            binding.placeInfo.visibility=View.GONE
            marker.tag = false
        }
    }

    fun displayDummyFeetStepInfoView(
        marker_type:Boolean,
        marker_num:Int
    ) {
        binding.placeInfo.visibility=View.VISIBLE

     //   if(marker_type) {
            v1.findViewById<CardView>(R.id.cardview_member).visibility = View.VISIBLE

            val textData: String = "내 발자취 ${marker_num+1}개"

            val spannable = SpannableStringBuilder(textData)
            val boldSpan = StyleSpan(Typeface.BOLD)

            if((marker_num+1)/10==0) {
                spannable.setSpan(boldSpan, 6, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                Log.d("ddd","marker_num%10==0 -> $marker_num 의 결과 :  ${marker_num%10}")
            }
            else if((marker_num+1)/10>0) {
                spannable.setSpan(boldSpan, 6, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                Log.d("ddd","marker_num%10>0 : $marker_num 의 결과 : ${marker_num%10}")

            }

            val colorSpan = ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.orange))

            if((marker_num+1)/10==0)
                spannable.setSpan(colorSpan, 6, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            else if((marker_num+1)/10>0)
                spannable.setSpan(colorSpan, 6, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


            v1.findViewById<TextView>(R.id.feetTextView).text = spannable
            v1.findViewById<TextView>(R.id.locationNameTextView).text = locationList[marker_num]

            v1.findViewById<ImageView>(R.id.thumbnailImageView).setImageResource(imageList[marker_num%4])
     //   }

     /*   else {
            v1.findViewById<CardView>(R.id.cardview_member).visibility = View.GONE

            v1.findViewById<TextView>(R.id.feetTextView).text = "방문하지 않은 곳"
            v1.findViewById<TextView>(R.id.locationNameTextView).text = locationList[marker_num]
        }
        */
        v1.setOnClickListener {
            Toast.makeText(mContext, "뷰 클릭됨", Toast.LENGTH_SHORT).show()
            //MapService(this).tryGetMapFootStepPopup(2)

        }

    }


    private fun subRoutine2(
        placeId:Int,
    ) {
        MapService(this).tryGetMapFootStepPopup(placeId)

    }

    private fun setMarkerClickEvent(
        placeId:Int,
        marker: Marker,
        tag: Boolean
    ) {
        var postingCount=0
        var locationName=""
        var imageUrl=""
        if (tag == false) {
            marker.tag = true
            binding.placeInfo.visibility = View.VISIBLE

            v1.findViewById<CardView>(R.id.cardview_member).visibility = View.VISIBLE
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            if (marker_info_hashMap.containsKey(placeId)){
                postingCount = marker_info_hashMap[placeId]!!.postingCount
                locationName=  marker_info_hashMap[placeId]!!.loacationName
                imageUrl=  marker_info_hashMap[placeId]!!.imageUrl
            }
            else
                Log.d("FootStepList", "실패")


            Log.d("FootStepList", "코루틴 진입후+ ${postingCount} ${locationName} ${imageUrl}")

            val textData: String = "내 발자취 ${postingCount}개"

            val spannable = SpannableStringBuilder(textData)
            val boldSpan = StyleSpan(Typeface.BOLD)

            if(postingCount/10==0) {
                spannable.setSpan(boldSpan, 6, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                       //Log.d("ddd","marker_num%10==0 -> $marker_num 의 결과 :  ${marker_num%10}")
            }
            else if(postingCount/10>0) {
                spannable.setSpan(boldSpan, 6, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                       //Log.d("ddd","marker_num%10>0 : $marker_num 의 결과 : ${marker_num%10}")
            }

            v1.findViewById<TextView>(R.id.feetTextView).text = spannable
            v1.findViewById<TextView>(R.id.locationNameTextView).text = locationName

            val Imageview=v1.findViewById<ImageView>(R.id.thumbnailImageView)//.setImageResource(imageList[marker_num%4])

            Glide.with(mContext).load(imageUrl).into(Imageview)

            v1.setOnClickListener {
                // Toast.makeText(mContext, "뷰 클릭됨", Toast.LENGTH_SHORT).show()

                val intent= Intent(requireContext(), MapGalleryActivity::class.java).apply {
                    putExtra("placeId", placeId)
                }
                startActivity(intent)
            }

        }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

        else{
            binding.placeInfo.visibility=View.GONE
            marker.tag = false
        }
    }


    override fun onGetMapFootStepListSuccess(response: AllResponse){//:HashMap<Int,Marker>{
        runBlocking {

            Log.d("FootStepList", "onGetMapFootStepListSuccess 되긴하니??")

            for (result_arr in response.result) {
                marker_hashMap[result_arr.placeId] = Marker().apply {
                    position = LatLng(result_arr.lat, result_arr.lng)
                    map = null
                    icon = OverlayImage.fromResource(R.drawable.foot3)
                    tag = false

                    val job=launch {
                        subRoutine2(result_arr.placeId)
                    }
                    job.join()
                }

                Log.d("FootStepList", "placeId: ${result_arr.placeId}")

            }
        }
        //return marker_hashMap
    }

    override fun onGetMapFootStepListFailure(message: String) {
        //showCustomToast("onGetMapFootStepListSuccess 오류 : $message")
    }

    override fun onGetMapFootStepPopupSuccess(response: PopupResponse,placeId:Int) {
        Log.d("FootStepList", "맵 프래그먼트 뷰 클릭됨+ ${response.result}")

        val arrData=ResultPopupListMap(
            response.result.imageUrl,
            response.result.loacationName,
            response.result.postingCount
        )

        marker_info_hashMap[placeId]=arrData
        Log.d("FootStepList", "해시맵에 데이터 담김+ ${marker_info_hashMap[placeId]!!.loacationName} " +
                "${marker_info_hashMap[placeId]!!.postingCount} ${marker_info_hashMap[placeId]!!.imageUrl}" )
    }

    override fun onGetMapFootStepPopupFailure(message: String) {
        //showCustomToast("오류 : $message")
    }

    override fun onGetMapFootStepSpecificSuccess(response: SpecificFstResponse) {
        var result_arr=response.result.allPlaceDto
        var cnt=0
        for ((key, value) in marker_hashMap) {

            if((cnt<result_arr.size)&&key==result_arr[cnt].placeId) {
                cnt++
                continue
            }
            value.map=null
        }
    }

    override fun onGetMapFootStepSpecificFailure(message: String) {
        //
    }

}