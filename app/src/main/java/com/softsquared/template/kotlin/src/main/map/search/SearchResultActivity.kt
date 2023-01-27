package com.softsquared.template.kotlin.src.main.map.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivitySearchResultBinding

class SearchResultActivity: BaseActivity<ActivitySearchResultBinding>(ActivitySearchResultBinding::inflate)
    , OnMapReadyCallback {
    private lateinit var naverMap: NaverMap
    private lateinit var v1: View

    var dataX:Double=0.0
    var dataY:Double=0.0
    var dataTitle:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras=intent.extras
        dataX=extras!!["positionX"] as Double//intent.getDoubleExtra("positionX",0.0)
        dataY=extras!!["positionY"] as Double
        dataTitle=extras!!["positionTitle"] as String

        Log.d("dataxy", "SearchResultActivity's Raw: $dataX, $dataY")

        v1 = layoutInflater.inflate(R.layout.feetstep_info, binding.searchPlaceInfo, true)

        binding.mapView2?.onCreate(savedInstanceState)
        binding.mapView2?.getMapAsync(this)
        initButtonView()

    }

    private fun initButtonView(){
        binding.mapSearchResultQuit.setOnClickListener {
            finish()
        }
    }
    override fun onStart() {
        super.onStart()
        binding.mapView2?.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView2?.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView2?.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView2?.onStop()
    }
    override fun onDestroy() {
        super.onDestroy()
        binding.mapView2?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView2?.onLowMemory()
    }
    override fun onMapReady(@NonNull naverMap: NaverMap) {
        this.naverMap = naverMap

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(dataY, dataX))
        naverMap.moveCamera(cameraUpdate)


        Marker().apply {
            position = LatLng(dataY, dataX)
            map = naverMap
            icon= OverlayImage.fromResource(R.drawable.footstep_orange)
            tag=false

           /* setOnClickListener {
                setMarkerClickEvent(true,this, tag as Boolean,0)

                true
            }

            */
        }
        displayFeetStepInfoView(true,0)

      /*  naverMap.setOnMapClickListener{  _, coord ->
            binding.searchPlaceInfo.visibility=View.GONE
        }

       */
    }
    fun setMarkerClickEvent(
        marker_type:Boolean,
        marker: Marker,
        tag: Boolean,
        marker_num:Int
    ) {
        if(tag==false){
            binding.searchPlaceInfo.visibility=View.GONE
            marker.tag = true

        }
        else{
            displayFeetStepInfoView(marker_type,marker_num)
            marker.tag = false
        }
    }

    fun displayFeetStepInfoView(
        marker_type:Boolean,
        marker_num:Int
    ){
        binding.searchPlaceInfo.visibility=View.VISIBLE

        v1.findViewById<CardView>(R.id.cardview_member).visibility = View.GONE

        v1.findViewById<TextView>(R.id.feetTextView).text = "방문하지 않은 곳"
        v1.findViewById<TextView>(R.id.titleTextView).text =  dataTitle
        v1.setOnClickListener{
            Toast.makeText(this, "뷰 클릭됨", Toast.LENGTH_SHORT).show() }
    }
}