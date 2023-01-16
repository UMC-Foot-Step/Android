package com.softsquared.template.kotlin.src.main.map

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.marginRight
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentMapBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.map.search.SearchActivity
import kotlin.concurrent.thread

class MapFragment :
    BaseFragment<FragmentMapBinding>(FragmentMapBinding::bind, R.layout.fragment_map),
    OnMapReadyCallback {
    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체
    private lateinit var naverMap: NaverMap
    lateinit var layoutParams:ConstraintLayout.LayoutParams

    private val markerList:ArrayList<Marker> = ArrayList()

   // private val latlngList=Array<Array<Double>>(3){Array<Double>(3){0.0} }
    //private val latlngList=Array(4){DoubleArray(2){0.0} }
    private val latlngList=arrayOf(arrayOf(37.56661,126.97839), arrayOf(37.56590, 126.98223),
       arrayOf(37.56905, 126.97767),arrayOf(37.33986, 126.74655))

    private lateinit var mContext: Context
    private lateinit var v1:View


    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 2. Context를 액티비티로 형변환해서 할당
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRetainInstance(true);
        /* Activity가 onDestroy되고 재생성될 때 Fragment도 같이 재생성된다. 하지만 setRetainInstance(true)를 설정하면
       onCreate, onDestory가 호출되지 않고 재사용된다.
       지금은 viewModel써서 하라는데 mvvm 모름..!
        */
        v1 = layoutInflater.inflate(R.layout.feetstep_info, binding.placeInfo, true)
        layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )


        binding.mapView?.onCreate(savedInstanceState)
        binding.mapView?.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        initButtonView()
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
                binding.btnTime.isSelected = false
            }
        }

        binding.btnTime.setOnClickListener {
            if (!binding.btnTime.isSelected) {
                binding.btnTime.background =
                    ContextCompat.getDrawable(mContext, R.drawable.time_btn_selected_background)
                binding.btnTime.isSelected = true
            } else {
                binding.btnTime.background =
                    ContextCompat.getDrawable(mContext, R.drawable.time_btn_background)
                binding.btnTime.isSelected = false
            }
        }

        binding.btnLocationCheck.setOnClickListener {
            if (!binding.btnLocationCheck.isSelected) {
                binding.btnLocationCheck.background =
                    ContextCompat.getDrawable(mContext, R.drawable.category_btn_selected_background)
                binding.btnLocationCheck.setTextColor(ContextCompat.getColor(mContext, R.color.white))
                binding.btnLocationCheck.isSelected = true
            } else {
                binding.btnLocationCheck.background =
                    ContextCompat.getDrawable(mContext, R.drawable.category_btn_background)
                binding.btnLocationCheck.setTextColor(ContextCompat.getColor(mContext, R.color.orange))
                binding.btnLocationCheck.isSelected = false
            }
        }

        binding.btnSearch.setOnClickListener{
            if (!binding.btnSearch.isSelected) {
                binding.btnSearch.isSelected = true

                val intent= Intent(requireContext(), SearchActivity::class.java)
                startActivity(intent)
            } else {
                binding.btnSearch.isSelected = false
            }
        }

        binding.btnPosition.setOnClickListener {
            naverMap.locationSource = locationSource
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
        }
    }


    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }


    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }


    override fun onMapReady(@NonNull naverMap: NaverMap) {
        this.naverMap = naverMap

        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow


        for((i,value) in latlngList.withIndex()) {
            //var marker1 =
            if(i<2) {
                Marker().apply {
                    position = LatLng(value[0], value[1])
                    map = naverMap
                    icon= OverlayImage.fromResource(R.drawable.footstep_orange)
                    tag=false

                    setOnClickListener {
                        val important = tag as Boolean
                        setImportant(true,this, important)

                        //desplayFeetStepInfoView(true)
                        //Toast.makeText(mContext, "마커 클릭됨", Toast.LENGTH_SHORT).show()
                        true
                    }
                }
            }

            else{
                Marker().apply {
                    position = LatLng(value[0], value[1])
                    map = naverMap
                    icon= OverlayImage.fromResource(R.drawable.foot3)
                    tag=false

                    setOnClickListener {
                        val important = tag as Boolean
                        setImportant(false,this, important)

                       // desplayFeetStepInfoView(false)
                        //Toast.makeText(mContext, "마커 클릭됨", Toast.LENGTH_SHORT).show()
                        true
                    }
                }
            }
        }

        naverMap.setOnMapClickListener{  _, coord ->
            binding.placeInfo.visibility=View.GONE
        }
    }

    fun setImportant(marker_type:Boolean,marker: Marker, important: Boolean) {
        if(important==false){
            desplayFeetStepInfoView(marker_type)
            marker.tag = true
        }
        else{
            binding.placeInfo.visibility=View.GONE
            marker.tag = false
        }
    }

    fun desplayFeetStepInfoView(marker_type:Boolean) {
        binding.placeInfo.visibility=View.VISIBLE

        v1.findViewById<TextView>(R.id.titleTextView).text = "장소이름"

        if(marker_type) {
            v1.findViewById<CardView>(R.id.cardview_member).visibility = View.VISIBLE
            //v1.

            val textData: String = "내 발자취 1개"

            val spannable = SpannableStringBuilder(textData)
            val boldSpan = StyleSpan(Typeface.BOLD)
            spannable.setSpan(boldSpan, 6, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            val colorSpan = ForegroundColorSpan( ContextCompat.getColor(mContext, R.color.orange))
            spannable.setSpan(colorSpan, 6, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            v1.findViewById<TextView>(R.id.priceTextView).text = spannable
        }

        else {
            v1.findViewById<CardView>(R.id.cardview_member).visibility = View.GONE
            v1.findViewById<TextView>(R.id.priceTextView).text = "방문하지 않은 곳"
        }

    }

}