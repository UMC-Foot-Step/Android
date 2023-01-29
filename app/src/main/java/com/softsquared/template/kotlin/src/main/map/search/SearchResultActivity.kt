package com.softsquared.template.kotlin.src.main.map.search

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
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
import com.softsquared.template.kotlin.src.main.gallery.map.MapGalleryActivity
import com.softsquared.template.kotlin.src.main.map.ResultPopupListMap
import com.softsquared.template.kotlin.src.main.map.model.PopupResponse2
import com.softsquared.template.kotlin.src.main.map.model.ResultPopupList2

class SearchResultActivity: BaseActivity<ActivitySearchResultBinding>(ActivitySearchResultBinding::inflate)
    , OnMapReadyCallback,SearchResultActivityInterface {
    private lateinit var naverMap: NaverMap
    private lateinit var v1: View
    lateinit var arrData:ResultPopupList2


    var dataX:Double=0.0
    var dataY:Double=0.0
    var dataTitle:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("생명주기", "서치리절트액티비티의 onCreate()")

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

        SearchService(this).tryGetMapSearchFootStep(dataY,dataX)
       // SearchService(this).tryGetMapSearchFootStep(37.5776087830657,126.976896737645)

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
            //displayFeetStepInfoView(marker_type)
            marker.tag = false
        }
    }

    fun displayFeetStepInfoView(
        marker_type:Boolean){
        binding.searchPlaceInfo.visibility=View.VISIBLE
        v1.findViewById<TextView>(R.id.locationNameTextView).text =  dataTitle

        if(marker_type==false) {
            v1.findViewById<CardView>(R.id.cardview_member).visibility = View.GONE

            v1.findViewById<TextView>(R.id.feetTextView).text = "방문하지 않은 곳"
            v1.setOnClickListener{
                Toast.makeText(this, "뷰 클릭됨", Toast.LENGTH_SHORT).show()
                //SearchService(this).tryGetMapSearchFootStep(37.5776087830657,126.976896737645)
            }
        }

        else{
            v1.findViewById<CardView>(R.id.cardview_member).visibility = View.VISIBLE

            val imgUrl=arrData.imageUrl
            val postingCount=arrData.postingCount

            val textData: String = "내 발자취 ${postingCount+1}개"

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

            val colorSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.orange))

            if(postingCount/10==0)
                spannable.setSpan(colorSpan, 6, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            else if(postingCount/10>0)
                spannable.setSpan(colorSpan, 6, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


            v1.findViewById<TextView>(R.id.feetTextView).text = spannable

            //v1.findViewById<ImageView>(R.id.thumbnailImageView).setImageResource(imgUrl)
            val Imageview=v1.findViewById<ImageView>(R.id.thumbnailImageView)//.setImageResource(imageList[marker_num%4])

            Glide.with(this).load(imgUrl).into(Imageview)
            v1.setOnClickListener{
                val intent= Intent(this, MapGalleryActivity::class.java).apply {
                    putExtra("placeId", arrData.placeId)
                }
                startActivity(intent)
            //SearchService(this).tryGetMapSearchFootStep(37.5776087830657,126.976896737645)
            }
        }


    }

    override fun onGetMapSearchFootStepSuccess(response: PopupResponse2) {
        Log.d("FootStepList", "서치리절트 액티비티 뷰 클릭됨+ ${response.result}")

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(dataY, dataX))
        naverMap.moveCamera(cameraUpdate)

        Marker().apply {
            position = LatLng(dataY, dataX)
            map = naverMap
            icon= OverlayImage.fromResource(R.drawable.footstep_orange)
            //tag=false
        }

        if(response.isSuccess==true) {
            arrData = ResultPopupList2(
                response.result.imageUrl,
                response.result.locationName,
                response.result.postingCount,
                response.result.placeId
            )
        }

        displayFeetStepInfoView(response.isSuccess)
    }

    override fun onGetMapSearchFootStepFailure(message: String) {
    }
}