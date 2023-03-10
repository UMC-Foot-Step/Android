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
import com.softsquared.template.kotlin.src.main.map.model.CityResponse
import com.softsquared.template.kotlin.src.main.map.model.PopupResponse
import com.softsquared.template.kotlin.src.main.map.model.SpecificFstResponse
import com.softsquared.template.kotlin.src.main.map.search.SearchActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope.coroutineContext
import java.time.LocalDate
import java.time.Month
import kotlin.properties.Delegates

class MapFragment(var city:String="") :
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
    private lateinit var locationSource: FusedLocationSource // ????????? ???????????? ?????????
    private lateinit var naverMap: NaverMap
    var firstRun=false

    var marker_info_hashMap=HashMap<Int, ResultPopupListMap>()
    var marker_hashMap=HashMap<Int,Marker>()
    var marker_mapChk=HashMap<Int,Boolean>()
    //private val markerList:ArrayList<Marker> = ArrayList()
    //private val latlngList=Array<Array<Double>>(3){Array<Double>(3){0.0} }
    //private val latlngList=Array(4){DoubleArray(2){0.0} }

    private lateinit var mContext: Context
    private lateinit var v1:View

    private lateinit var bottomSheet:View
    private lateinit var bottomSheet2:View

    private lateinit var cal_date1:TextView
    private lateinit var cal_date2:TextView

    var setCalBtnClickEventCnk=false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FootStepList","??? ?????????????????? onViewCreated")

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

        //???????????? ??????
        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        initButtonView()
    }


    override fun onAttach(context: Context) {
        runBlocking {
            super.onAttach(context)

            mContext = context
            Log.d("???????????????", "??? ?????????????????? onAttach city +"+city)
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

            // date bottomsheetdialog ??????
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
                setCalBtnClickEvent(1)

                Log.d("FootStepList","btnCal1 ?????????")

                data2 = cal_date2.text.toString()

                Log.d("Calender","cal 1 $tvYear-$tvMonth-$tvDay")
            }

            btnCal2.setOnClickListener{
                setCalBtnClickEvent(2)
                Log.d("FootStepList","btnCal2 ?????????")

                Log.d("Calender","cal2 ${date2IntArr.year}-${date2IntArr.month}-${date2IntArr.day}")
            }

            btnChk.setOnClickListener {
                runBlocking {

                    if (setCalBtnClickEventCnk) {
                        data1 = cal_date1.text.toString()
                        data2 = cal_date2.text.toString()

                        setCalBtnClickEventCnk = false
                    }
                    Log.d("???????????????", "btnChk-Calender ?????? ${data1} ${data2}")

                    val job = launch {
                        // CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.IO) {
                            tryGetMapFootStepSpecific(data1, data2)
                            Log.d("???????????????", "btnChk ??????????????????")
                        }
                    }
                    //job.join()
                    Log.d("???????????????", "btnChk ???????????? ?????????")

                }
                CoroutineScope(Dispatchers.Main).launch {

                    for ((key, value) in marker_hashMap) {
                        if (marker_mapChk[key] == false) {
                            value.map = null

                            Log.d("???????????????", "????????? ?????? ???????????? ?????? ${key}")
                        } else {
                            marker_mapChk[key] = false
                            value.map = naverMap

                            Log.d("???????????????", "????????? ?????? ????????? ?????? ${key}")
                        }
                    }
                }

                    btnR.isChecked = false
                    btnR2.isChecked = false
                    btnR3.isChecked = false
                    btnR4.isChecked = false
                    btnR5.isChecked = false
                    directSelectLayout.visibility = View.GONE
                    cal_date1.text = today.toString()
                    cal_date2.text = today.toString()

                    bottomSheetDialog.dismiss()

                    Log.d("???????????????", "btnChk ??????")


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
                    Log.d("???????????????", "binding.btnPosition-???????????? ?????? + $key")
                    value.map = naverMap
                }
            }

            naverMap.locationSource = locationSource
            naverMap.locationTrackingMode = LocationTrackingMode.Follow

        }
    }

    fun setBottomSheetDialogClose(){

    }

    private suspend fun tryGetMapFootStepSpecific(
        start_date:String,
        end_date:String){
        MapService(this).tryGetMapFootStepSpecific(start_date,end_date)
    }

    private fun setCalBtnClickEvent(btn_type:Int) {
        Log.d("Calender","setCalBtnClickEvent + $btn_type ?????????")
        setCalBtnClickEventCnk=true

        val bottomSheetDialog = BottomSheetDialog(mContext)

        val btnClose = bottomSheet2.findViewById<ImageButton>(R.id.dateBtnQuit)
        val btnDateCheck = bottomSheet2.findViewById<Button>(R.id.dateBtnCheck)
        val calView = bottomSheet2.findViewById<CalendarView>(R.id.calenderView)

        calView.maxDate = System.currentTimeMillis()

        // calendar bottomsheetdialog ??????
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

                Log.d("Calender","setCalBtnClickEvent + btnDateCheck + $btn_type ????????? $tvYear-$tvMonth-$tvDay")
            }
            else{
                cal_date2.text="$tvYear-$tvMonth-$tvDay"

                Log.d("Calender","setCalBtnClickEvent + btnDateCheck + $btn_type ????????? $tvYear-$tvMonth-$tvDay")
            }
            bottomSheetDialog.dismiss()
        }
    }


    override fun onStart() = runBlocking<Unit>{
        super.onStart()
        Log.d("???????????????","??? ?????????????????? onStart()")

        val startJob = launch {
            withContext(Dispatchers.IO) {
                tryGetMapFootStepList()

                Log.d("???????????????", "onStart()-tryGetMapFootStepList ?????????")
            }
        }
        startJob.join()
        Log.d("???????????????", "onStart startJob.join()????????? startJob.isActive : ${startJob.isActive}")

        binding.mapView.onStart()
        Log.d("???????????????", "onStart ????????????")
    }
    private suspend fun tryGetMapFootStepList(){
        MapService(this).tryGetMapFootStepList()
    }

    private suspend fun tryGetMapFootStepCity(city:String){
        MapService(this).tryGetMapFootStepCity(city)
    }

    override fun onResume() {
        super.onResume()
        Log.d("???????????????","??? ?????????????????? onResume()")

        if(firstRun){
            //Log.d("????????????","??? ?????????????????? onResume()")
            CoroutineScope(Dispatchers.Main).launch {
                //subRoutine4()
                for ((key, value) in marker_hashMap) {
                    Log.d("???????????????", "onResume() ???????????? ?????? ??????+ $key")
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
        Log.d("???????????????", "onResume() ???????????? ?????????")
    }


    override fun onPause() {
        super.onPause()
        Log.d("FootStepList","??? ?????????????????? onPause()")

        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        Log.d("FootStepList","??? ?????????????????? onStop()")

        binding.mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d("FootStepList","??? ?????????????????? onLowMemory()")

        binding.mapView.onLowMemory()
    }


    override fun onMapReady(@NonNull naverMap: NaverMap) {
        Log.d("???????????????", "onMapReady")
        //Log.d("????????????", "onMapReady")

        if (!firstRun) {
            firstRun = true
        }

        this.naverMap = naverMap

       // val mapScope= CoroutineScope()

//onStart??? ??????
        CoroutineScope(Dispatchers.Main).launch{
            Log.d("???????????????", "CoroutineScope(Dispatchers.Main).launch ${Thread.currentThread().name}")

            for ((key, value) in marker_hashMap) {
                Log.d("???????????????", "onMapReady ???????????? ?????? ??????+ $key")
                value.map = naverMap
                marker_mapChk[key] = false
                value.setOnClickListener {
                    setMarkerClickEvent(key, value, value.tag as Boolean)
                    true
                }
            }
        }


                // }
                // }
                /*
                CoroutineScope(Dispatchers.Main).launch {
                    for ((key, value) in marker_hashMap) {
                        if (marker_mapChk[key] == false) {
                            value.map = null

                            Log.d("???????????????", "onMapReady ????????? ?????? ???????????? ?????? ${key}")
                        } else {
                            marker_mapChk[key] = false

                            Log.d("???????????????", "onMapReady ????????? ?????? ????????? ?????? ${key}")
                        }
                    }
                }

                 */



        displayCityOutput()
        Log.d("???????????????", "onMapReady if (city !=  { ??????")

        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        naverMap.setOnMapClickListener { _, coord ->
            binding.placeInfo.visibility = View.GONE
        }
    }

    fun displayCityOutput(){
        if (city != "") {
            // runBlocking {
            //    launch {
            //        withContext(Dispatchers.IO) {
            Log.d("???????????????", "displayCityOutput " + city)

            CoroutineScope(Dispatchers.IO).launch {
                Log.d(
                    "???????????????",
                    "CoroutineScope(Dispatchers.IO).launch ${Thread.currentThread().name}"
                )

                tryGetMapFootStepCity(city)
                city = ""
            }
            Log.d("???????????????", "displayCityOutput-tryGetMapFootStepCity " + city)
        }
    }
    private suspend fun tryGetMapFootStepPopup(
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
                Log.d("???????????????", "??????")

            Log.d("???????????????", "onMapReady onResume ????????? marker_info_hashmap??? ??????+ ${postingCount} ${locationName} ${imageUrl}")

            val textData: String = "??? ????????? ${postingCount}???"

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

    override suspend fun onGetMapFootStepCitySuccess(response: CityResponse) {
        Log.d("???????????????", "onGetMapFootStepCitySuccess ??????????????")

        if (response.result == null) {
            Log.d("???????????????", "5?????? api null")

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed(Runnable { Toast.makeText(mContext, "?????? ????????? ????????????", Toast.LENGTH_SHORT).show() }, 0)
            //Toast.makeText(mContext, "?????? ????????? ????????????", Toast.LENGTH_SHORT).show()
        }

        else{
            Log.d("???????????????", "5?????? api ${response.toString()}")

            if(response.result.size==0){
                //Toast.makeText(mContext, "?????? ????????? ????????????", Toast.LENGTH_SHORT).show()
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed(Runnable { Toast.makeText(mContext, "?????? ????????? ????????????", Toast.LENGTH_SHORT).show() }, 0)
                return
            }

            CoroutineScope(Dispatchers.Main).launch {
                Log.d("???????????????", "CoroutineScope(Dispatchers.Main).launch ??????..???")

                for (result_arr in response.result) {

                    marker_mapChk[result_arr.placeId] = true

                    Log.d("???????????????", "onGetMapFootStepCitySuccess ????????? ?????? ????????? ?????? ${result_arr.placeId}")

                }

                 for ((key, value) in marker_hashMap) {
                    if (marker_mapChk[key] == false) {
                        value.map = null

                        Log.d("???????????????", "onGetMapFootStepCitySuccess ????????? ?????? ???????????? ?????? ${key}")
                    } else {
                        marker_mapChk[key] = false
                        value.map = naverMap

                        Log.d("???????????????", "onGetMapFootStepCitySuccess ????????? ?????? ????????? ?????? ${key}")
                    }
                }
            }


           // }
        }
    }

    override fun onGetMapFootStepCityFailure(message: String) {
//
    }

    //onStart??? ??????
    override fun onGetMapFootStepListSuccess(response: AllResponse)=runBlocking<Unit> {
        Log.d("???????????????", "onGetMapFootStepListSuccess ??????????????")

        for (result_arr in response.result) {
            marker_hashMap[result_arr.placeId] = Marker().apply {
                position = LatLng(result_arr.lat, result_arr.lng)
                map = null
                icon = OverlayImage.fromResource(R.drawable.footstep_orange3)
                tag = false

                val job = launch {
                    withContext(Dispatchers.IO) {
                        tryGetMapFootStepPopup(result_arr.placeId)
                    }
                }
                job.join()

                Log.d("???????????????", "onGetMapFootStepListSuccess ????????? placeId: ${result_arr.placeId}")
            }
        }
    }

    override fun onGetMapFootStepListFailure(message: String) {
        //showCustomToast("onGetMapFootStepListSuccess ?????? : $message")
    }

    override fun onGetMapFootStepPopupSuccess(response: PopupResponse,placeId:Int) =runBlocking<Unit>{
        val job = launch {
            Log.d("???????????????", "onGetMapFootStepPopupSuccess + ${response.result}")
        }
        job.join()

        //Log.d("???????????????", "???????????? 2 ?????????..")

        if(!marker_info_hashMap.containsKey(placeId)) {
            val arrData = ResultPopupListMap(
                response.result.imageUrl,
                response.result.loacationName,
                response.result.postingCount
            )
            marker_info_hashMap[placeId] = arrData
        }
        Log.d(
            "???????????????",
            "onGetMapFootStepPopupSuccess ????????? + ${marker_info_hashMap[placeId]!!.loacationName} " +
                        "${marker_info_hashMap[placeId]!!.postingCount} ${marker_info_hashMap[placeId]!!.imageUrl}")

    }

    override fun onGetMapFootStepPopupFailure(message: String) {
        //showCustomToast("?????? : $message")
    }

    override suspend fun onGetMapFootStepSpecificSuccess(response: SpecificFstResponse) {//=runBlocking<Unit>{
        Log.d("???????????????", "onGetMapFootStepSpecificSuccess ?????????????")

            if (response.result == null) {
                Log.d("???????????????", "4?????? api null")
                val handler = Handler(
                    Looper.getMainLooper()
                )
                handler.postDelayed(Runnable {
                    Toast.makeText(
                        mContext,
                        "?????? ????????? ????????????",
                        Toast.LENGTH_SHORT
                    ).show()
                }, 0)
                Log.d("???????????????", "?????? ????????? ????????????")

            }


        else {
            Log.d("???????????????", "4?????? api ${response.toString()}")


              //  CoroutineScope(Dispatchers.Main).launch {
                    //Log.d("???????????????", "onGetMapFootStepSpecificSuccess??? CoroutineScope ??????..???")

                    for (result_arr in response.result.allPlaceDto) {

                        marker_mapChk[result_arr.placeId] = true
                        Log.d("???????????????", "????????? ?????? ????????? ?????? ${result_arr.placeId}")
                    }

                    /*for ((key, value) in marker_hashMap) {
                        if (marker_mapChk[key] == false) {
                            value.map = null

                            Log.d("???????????????", "????????? ?????? ???????????? ?????? ${key}")
                        } else {
                            marker_mapChk[key] = false
                            Log.d("???????????????", "????????? ?????? ????????? ?????? ${key}")
                        }
                    }

                     */
             //   }

        }

        //val job=launch(Dispatchers.Main){
        /*for ((key, value) in marker_hashMap) {
              Log.d("FootStepList", "????????? ?????? ?????? ??????")

              if((cnt<result_arr.size)&&key==result_arr[cnt].placeId) {
                    cnt++
                    continue
                }
               value.map=null

               Log.d("FootStepList", "????????? ???????????? ????????? ?????? ${key}")
            }
            */

        Log.d("???????????????", "onGetMapFootStepSpecificSuccess ???????????? ??????")
    }

    override fun onGetMapFootStepSpecificFailure(message: String) {
        //
    }


    private fun subRoutine5(response: SpecificFstResponse) {

        var result_arr=response.result.allPlaceDto
        var cnt=0

        for ((key, value) in marker_hashMap) {
            Log.d("Calender", "????????? ?????? ?????? ??????")

            if((cnt<result_arr.size)&&key==result_arr[cnt].placeId) {
                cnt++
                continue
            }
            Log.d("Calender", "????????? ???????????? ????????? ?????? ${key}")
            value.map=null
        }
    }

    private suspend fun subRoutine6() {
        for ((key, value) in marker_hashMap) {
            if (marker_mapChk[key] == true) {
                Log.d("Calender", "????????? ?????? ${key}")
                marker_mapChk[key]=false

                value.map = null
            }
        }

    }

}

