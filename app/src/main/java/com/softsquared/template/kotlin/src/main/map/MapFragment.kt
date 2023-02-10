package com.softsquared.template.kotlin.src.main.map

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.service.autofill.Validators.or
import android.system.Os.bind
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
import java.time.Month
import kotlin.properties.Delegates

class MapFragment :
    BaseFragment<FragmentMapBinding>(FragmentMapBinding::bind, R.layout.fragment_map),
    OnMapReadyCallback,MapFragmentInterface {

    // datepicker
    private val today = LocalDate.now()
    private var tvYear = today.year
    private var tvMonth = today.monthValue
    private var tvDay = today.dayOfMonth

    var date1IntArr=IntArr(tvYear,tvMonth,tvDay)
    var date2IntArr=IntArr(tvYear,tvMonth,tvDay)

    data class IntArr(
        val year:Int,
        val month:Int,
        val day:Int
    )

    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체
    private lateinit var naverMap: NaverMap
    var firstRun=false

    var marker_info_hashMap=HashMap<Int, ResultPopupListMap>()
    var marker_hashMap=HashMap<Int,Marker>()
    var marker_mapChk=HashMap<Int,Boolean>()
    //private val markerList:ArrayList<Marker> = ArrayList()
    //private val latlngList=Array<Array<Double>>(3){Array<Double>(3){0.0} }
    //private val latlngList=Array(4){DoubleArray(2){0.0} }

    val imageList=arrayOf(R.drawable.cat_dummy, R.drawable.im2_dummy, R.drawable.google_dummy, R.drawable.chimchakman)

    private lateinit var mContext: Context
    private lateinit var v1:View

    private lateinit var bottomSheet:View
    private lateinit var bottomSheet2:View

    private lateinit var cal_date1:TextView
    private lateinit var cal_date2:TextView

    var setCalBtnClickEventCnk=false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FootStepList","맵 프래그먼트의 onViewCreated")
        //Log.d("생명주기","맵 프래그먼트의 onViewCreated")

        setRetainInstance(true);
        /* Activity가 onDestroy되고 재생성될 때 Fragment도 같이 재생성된다. 하지만 setRetainInstance(true)를 설정하면
       onCreate, onDestory가 호출되지 않고 재사용된다.->현재는 mvvm패턴의 viewModel써서 하라는데 몰라서 저거 일단 둠
        */
        v1 = layoutInflater.inflate(R.layout.feetstep_info, binding.placeInfo, true)
        bottomSheet = layoutInflater.inflate(R.layout.fragment_map_time_setting, null)
        bottomSheet2 = layoutInflater.inflate(R.layout.fragment_map_calendar, null)

        cal_date1 = bottomSheet.findViewById(R.id.dateText1)
        cal_date2 = bottomSheet.findViewById(R.id.dateText2)

        var date = "$tvYear-$tvMonth-$tvDay"
        cal_date1.text=date
        cal_date2.text=date

        binding.mapView?.onCreate(savedInstanceState)
        binding.mapView?.getMapAsync(this)

        //현재위치 기능
        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        initButtonView()
    }


    override fun onAttach(context: Context) {
        runBlocking {
            super.onAttach(context)

            Log.d("FootStepList", "맵 프래그먼트의 onAttach")
            //Log.d("생명주기", "맵 프래그먼트의 onAttach")


            // 메인 액티비티 context 획득
            mContext = context
            Log.d("FootStepList", "onAttach 마지막")
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

            val btnChk=bottomSheet.findViewById<TextView>(R.id.periodBtnCheck)

            val directSelectLayout=bottomSheet.findViewById<ConstraintLayout>(R.id.direct_select_layout)

            // date bottomsheetdialog 설정
            if(bottomSheet.parent!=null) {
                var p=bottomSheet.parent as ViewGroup
                p.removeView(bottomSheet)
            }
            bottomSheetDialog.setContentView(bottomSheet)
            bottomSheetDialog.show()

            lateinit var data1:String
            var data2=today.toString()

            btnClose.setOnClickListener {
                setCalBtnClickEventCnk=false

                btnR.isChecked=false
                btnR2.isChecked=false
                btnR3.isChecked=false
                btnR4.isChecked=false
                btnR5.isChecked=false
                directSelectLayout.visibility=View.GONE
                cal_date1.text=today.toString()
                cal_date2.text=today.toString()

                bottomSheetDialog.dismiss()
            }

            btnR.setOnClickListener {
                data1=today.toString()

                directSelectLayout.visibility=View.GONE
                Log.d("FootStepList","btnR")
            }
            btnR2.setOnClickListener {
                data1=today.minusWeeks(1).toString()

                directSelectLayout.visibility=View.GONE
                Log.d("FootStepList","btnR2")
            }
            btnR3.setOnClickListener {
                data1=today.minusMonths(1).toString()

                directSelectLayout.visibility=View.GONE
                Log.d("FootStepList","btnR3")
            }
            btnR4.setOnClickListener {
                data1=today.minusMonths(3).toString()

                directSelectLayout.visibility=View.GONE
                Log.d("FootStepList","btnR4")
            }
            btnR5.setOnClickListener {
                directSelectLayout.visibility=View.VISIBLE

                Log.d("FootStepList","btnR5")
            }

            btnCal1.setOnClickListener{
                Log.d("FootStepList","btnCal1 들어가기 전")

                setCalBtnClickEvent(1)

                Log.d("FootStepList","btnCal1 들어감")

                //data1 = "${date1IntArr.year}-${date1IntArr.month}-${date1IntArr.day}" //cal_date1.text.toString()
                data2 = cal_date2.text.toString()

                Log.d("Calender","cal 1 $tvYear-$tvMonth-$tvDay")
            }

            btnCal2.setOnClickListener{
                setCalBtnClickEvent(2)
                Log.d("FootStepList","btnCal2 들어감")
                //data2="${date2IntArr.year}-${date2IntArr.month}-${date2IntArr.day}"

                Log.d("Calender","cal2 ${date2IntArr.year}-${date2IntArr.month}-${date2IntArr.day}")
            }

            btnChk.setOnClickListener {
                runBlocking {

                    if (setCalBtnClickEventCnk) {
                        data1 = cal_date1.text.toString()
                        data2 = cal_date2.text.toString()

                        setCalBtnClickEventCnk = false
                    }
                    Log.d("FootStepList", "Calender 뭐지 ${data1} ${data2}")

                    val job = launch {
                        withContext(Dispatchers.IO) {
                            subRoutine3(data1, data2)
                            Log.d("FootStepList", "Calender 런블락킹 들어갔냐")
                        }
                    }
                    job.join()


                    btnR.isChecked = false
                    btnR2.isChecked = false
                    btnR3.isChecked = false
                    btnR4.isChecked = false
                    btnR5.isChecked = false
                    directSelectLayout.visibility = View.GONE
                    cal_date1.text = today.toString()
                    cal_date2.text = today.toString()

                    bottomSheetDialog.dismiss()
                    Log.d("FootStepList", "Calender 런블락킹 안끝남??")

                }
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
            if(firstRun) {
                for ((key, value) in marker_hashMap) {
                    Log.d("FootStepList", "해시마커 포문 안되니+ $key")
                    value.map = naverMap
                }
            }

            naverMap.locationSource = locationSource
            naverMap.locationTrackingMode = LocationTrackingMode.Follow

        }
    }

    fun setBottomSheetDialogClose(){

    }

    private suspend fun subRoutine3(start_date:String,end_date:String){
        MapService(this).tryGetMapFootStepSpecific(start_date,end_date)
    }

    private fun setCalBtnClickEvent(btn_type:Int) {
        Log.d("Calender","setCalBtnClickEvent + $btn_type 들어감")
        setCalBtnClickEventCnk=true

        val bottomSheetDialog = BottomSheetDialog(mContext)

        val btnClose = bottomSheet2.findViewById<ImageButton>(R.id.dateBtnQuit)
        val btnDateCheck = bottomSheet2.findViewById<Button>(R.id.dateBtnCheck)
        val calView = bottomSheet2.findViewById<CalendarView>(R.id.calenderView)

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
            if(btn_type==1){
                cal_date1.text="$tvYear-$tvMonth-$tvDay"

                Log.d("Calender","setCalBtnClickEvent + btnDateCheck + $btn_type 들어감 $tvYear-$tvMonth-$tvDay")
            }
            else{
                cal_date2.text="$tvYear-$tvMonth-$tvDay"

                Log.d("Calender","setCalBtnClickEvent + btnDateCheck + $btn_type 들어감 $tvYear-$tvMonth-$tvDay")
            }
            bottomSheetDialog.dismiss()
        }
    }

    private fun setData(
        year: Int,
        month: Int,
        day: Int,
        btn_type: Int){
        var year1 =date1IntArr.year
        var month1 =date1IntArr.month
        var day1 =date1IntArr.day
        Log.d("Calender","setCalBtnClickEvent + btnDateCheck + setData 들어감")

        if(year1>year){
            Toast.makeText(mContext, "날짜 범위에 맞지 않습니다", Toast.LENGTH_SHORT).show()
            Log.d("Calender","${date1IntArr.toString()} + $year/$month/$day")
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
                    cal_date2.text="$year-$month-$day"
                }
            }
        }
    }

    override fun onStart() = runBlocking<Unit>{
        super.onStart()
        Log.d("FootStepList","맵 프래그먼트의 onStart()")
        //Log.d("생명주기", "맵 프래그먼트의 onStart()")

        val startJob = launch {
            withContext(Dispatchers.IO) {
                subRoutine()
                Log.d("FootStepList", "onStart 서브루틴1 지나감")
            }

        }

        startJob.join()
        Log.d("FootStepList", "onStart startJob.join() 지나감 startJob.isActive ${startJob.isActive}")

        binding.mapView.onStart()
        Log.d("FootStepList", "onStart 런블락킹")
    }

    override fun onResume() {//=runBlocking<Unit>{
        super.onResume()
        Log.d("FootStepList","맵 프래그먼트의 onResume()")

        if(firstRun){
            //Log.d("생명주기","맵 프래그먼트의 onResume()")
            CoroutineScope(Dispatchers.Main).launch {
                //subRoutine4()
                for ((key, value) in marker_hashMap) {
                    Log.d("FootStepList", "onResume() 해시마커 포문 안되니+ $key")
                    value.map = naverMap
                    marker_mapChk[key]=false
                    value.setOnClickListener {
                        setMarkerClickEvent(key, value, value.tag as Boolean)
                        true
                    }
                }
            }
        }

        binding.mapView.onResume()
        Log.d("FootStepList", "onResume() 런블락킹 마지막")
    }

    override fun onPause() {
        super.onPause()
        Log.d("FootStepList","맵 프래그먼트의 onPause()")

        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        Log.d("FootStepList","맵 프래그먼트의 onStop()")

        binding.mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d("FootStepList","맵 프래그먼트의 onLowMemory()")

        binding.mapView.onLowMemory()
    }

    private suspend fun subRoutine(){
        MapService(this).tryGetMapFootStepList()
    }

    private fun subRoutine4(){
        for ((key, value) in marker_hashMap) {
            Log.d("FootStepList", "onResume() 해시마커 포문 안되니+ $key")
            value.map = naverMap
            marker_mapChk[key]=false
            value.setOnClickListener {
                setMarkerClickEvent(key, value, value.tag as Boolean)
                true
            }
        }
    }

    override fun onMapReady(@NonNull naverMap: NaverMap) {
        Log.d("FootStepList", "onMapReady")
        //Log.d("생명주기", "onMapReady")

        if(!firstRun) {
            firstRun=true
        }

        this.naverMap = naverMap

        Log.d("FootStepList", "해시마커 포문직전")

//onStart에 있음
        CoroutineScope(Dispatchers.Main).launch {
            for ((key, value) in marker_hashMap) {
                Log.d("FootStepList", "해시마커 포문 안되니+ $key")
                value.map = naverMap
                marker_mapChk[key]=false
                value.setOnClickListener {
                    setMarkerClickEvent(key, value, value.tag as Boolean)
                    true
                }
            }
        }

        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        naverMap.setOnMapClickListener{  _, coord ->
            binding.placeInfo.visibility=View.GONE
        }
    }

    private suspend fun subRoutine2(
        placeId:Int
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
            }
            else if(postingCount/10>0) {
                spannable.setSpan(boldSpan, 6, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            val colorSpan = ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.orange))

            if(postingCount/10==0)
                spannable.setSpan(colorSpan, 6, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            else if(postingCount/10>0)
                spannable.setSpan(colorSpan, 6, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            v1.findViewById<TextView>(R.id.feetTextView).text = spannable
            v1.findViewById<TextView>(R.id.locationNameTextView).text = locationName

            val Imageview=v1.findViewById<ImageView>(R.id.thumbnailImageView)

            Glide.with(mContext).load(imageUrl).into(Imageview)

            v1.setOnClickListener {
                // Toast.makeText(mContext, "뷰 클릭됨", Toast.LENGTH_SHORT).show()

                val intent= Intent(requireContext(), MapGalleryActivity::class.java).apply {
                    putExtra("placeId", placeId)
                }
                startActivity(intent)
            }
        }

        else{
            binding.placeInfo.visibility=View.GONE
            marker.tag = false
        }
    }

    //onStart에 있음
    override fun onGetMapFootStepListSuccess(response: AllResponse)=runBlocking<Unit> {
        Log.d("FootStepList", "onGetMapFootStepListSuccess 되긴하니??")

        for (result_arr in response.result) {
            marker_hashMap[result_arr.placeId] = Marker().apply {
                position = LatLng(result_arr.lat, result_arr.lng)
                map = null
                icon = OverlayImage.fromResource(R.drawable.footstep_orange3)
                tag = false

                val job = launch {
                    withContext(Dispatchers.IO) {
                        subRoutine2(result_arr.placeId)
                    }
                }
                job.join()

                Log.d("FootStepList", "서브루틴1 placeId: ${result_arr.placeId}")
            }
        }
    }

    override fun onGetMapFootStepListFailure(message: String) {
        //showCustomToast("onGetMapFootStepListSuccess 오류 : $message")
    }

    override fun onGetMapFootStepPopupSuccess(response: PopupResponse,placeId:Int) =runBlocking<Unit>{
        val job = launch {
            Log.d("FootStepList", "서브루틴 2 마커로...+ ${response.result}")
        }
        job.join()

        Log.d("FootStepList", "서브루틴 2 마커로..")

        if(!marker_info_hashMap.containsKey(placeId)) {
            val arrData = ResultPopupListMap(
                response.result.imageUrl,
                response.result.loacationName,
                response.result.postingCount
            )
            marker_info_hashMap[placeId] = arrData
        }
        Log.d(
            "FootStepList",
            "서브루틴 2 해시맵에 데이터 담김+ ${marker_info_hashMap[placeId]!!.loacationName} " +
                        "${marker_info_hashMap[placeId]!!.postingCount} ${marker_info_hashMap[placeId]!!.imageUrl}")

    }

    override fun onGetMapFootStepPopupFailure(message: String) {
        //showCustomToast("오류 : $message")
    }

    override suspend fun onGetMapFootStepSpecificSuccess(response: SpecificFstResponse) {//=runBlocking<Unit>{
        Log.d("FootStepList", "onGetMapFootStepSpecificSuccess 되긴하니?")

            if (response.result == null) {
                Log.d("FootStepList", "4번째 api null")
                val handler = Handler(
                    Looper.getMainLooper()
                )
                handler.postDelayed(Runnable {
                    Toast.makeText(
                        mContext,
                        "조회 결과가 없습니다",
                        Toast.LENGTH_SHORT
                    ).show()
                }, 0)
            }


        else {
            Log.d("FootStepList", "4번째 api ${response.toString()}")
                //var result_arr=response.result.allPlaceDto
                //var cnt=0
         //
            //launch(Dispatchers.Main) {
                    //launch {
                        Log.d("FootStepList", "launch 진입..???")


                   // }.join()

                    Log.d("FootStepList", "첫번째 포문 못나간건강???")

                    CoroutineScope(Dispatchers.Main).launch {
                        Log.d("FootStepList", "CoroutineScope(Dispatchers.Main).launch 진입..???")

                        for (result_arr in response.result.allPlaceDto) {

                            marker_mapChk[result_arr.placeId] = true

                            Log.d("FootStepList", "첫번째 포문 보이는 마커 ${result_arr.placeId}")

                        }

                        for ((key, value) in marker_hashMap) {
                            if (marker_mapChk[key] == false) {
                                value.map = null

                                Log.d("FootStepList", "두번째 포문 안보이는 마커 ${key}")
                            } else {
                                marker_mapChk[key] = false

                                Log.d("FootStepList", "두번째 포문 보이는 마커 ${key}")
                            }
                        }
                    }

                }

                    //val job=launch(Dispatchers.Main){
                    /*for ((key, value) in marker_hashMap) {
              Log.d("FootStepList", "첫번째 포문 도는 횟수")

              if((cnt<result_arr.size)&&key==result_arr[cnt].placeId) {
                    cnt++
                    continue
                }
               value.map=null

               Log.d("FootStepList", "첫번째 포문에서 안보일 마커 ${key}")
            }
            */



        Log.d("FootStepList", "onGetMapFootStepSpecificSuccess 런블락킹 나감")

    }

    override fun onGetMapFootStepSpecificFailure(message: String) {
        //
    }

    private fun subRoutine5(response: SpecificFstResponse) {
       /* for (result_arr in response.result.allPlaceDto) {
            if (marker_info_hashMap.containsKey(result_arr.placeId)) {
                continue
            } else {
                Log.d("Calender", "첫번째 포문 ${result_arr.placeId}")

                marker_mapChk[result_arr.placeId] = true
            }
        }

        */
        var result_arr=response.result.allPlaceDto
        var cnt=0

        for ((key, value) in marker_hashMap) {
            Log.d("Calender", "첫번째 포문 도는 횟수")

            if((cnt<result_arr.size)&&key==result_arr[cnt].placeId) {
                cnt++
                continue
            }
            Log.d("Calender", "첫번째 포문에서 안보일 마커 ${key}")
            value.map=null
        }
    }

    private suspend fun subRoutine6() {
        for ((key, value) in marker_hashMap) {
            if (marker_mapChk[key] == true) {
                Log.d("Calender", "두번째 포문 ${key}")
                marker_mapChk[key]=false

                value.map = null
            }
        }

    }

}

