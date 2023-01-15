package com.softsquared.template.kotlin.src.main.map

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentMapBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import kotlin.concurrent.thread

class MapFragment :
    BaseFragment<FragmentMapBinding>(FragmentMapBinding::bind, R.layout.fragment_map),
    OnMapReadyCallback {
    private lateinit var mContext: Context
    private lateinit var v1:View
    val btnSearchInt:Int=1
    val btnPositionInt:Int=2

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

        binding.mapView?.onCreate(savedInstanceState)
        binding.mapView?.getMapAsync(this)

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
                binding.subBtnLayout.visibility = View.GONE
                binding.btnMenu.isSelected = false
            }
        }

        binding.btnTime.setOnClickListener {
            if (!binding.btnTime.isSelected) {
                binding.btnTime.background =
                    ContextCompat.getDrawable(mContext, R.drawable.time_btn_selected_background)
                binding.btnTime.isSelected = true
            } else if (binding.btnTime.isSelected){
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
                desplayFeetStepInfoView(btnSearchInt)
                binding.btnSearch.isSelected = true
            } else {
                binding.placeInfo.visibility=View.GONE
                binding.btnSearch.isSelected = false
            }
        }

        binding.btnPosition.setOnClickListener {
            if (!binding.btnPosition.isSelected) {
                desplayFeetStepInfoView(btnPositionInt)
                binding.btnPosition.isSelected = true
            } else {
                binding.placeInfo.visibility=View.GONE
                binding.btnPosition.isSelected = false
            }
        }
    }

    fun desplayFeetStepInfoView(btn:Int) {
        binding.placeInfo.visibility=View.VISIBLE

        v1.findViewById<TextView>(R.id.titleTextView).text = "장소이름"

        if(btn==btnPositionInt) {
            v1.findViewById<CardView>(R.id.cardview_member).visibility = View.VISIBLE
            v1.findViewById<TextView>(R.id.priceTextView).text = "내 발자취 1개"
        }
        else {
            v1.findViewById<CardView>(R.id.cardview_member).visibility = View.GONE
            v1.findViewById<TextView>(R.id.priceTextView).text = "방문하지 않은 곳"
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

    //override fun onDestroyView() {
    //    super.onDestroyView()
    //    binding.mapView?.onDestroy()
    //}

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }


    override fun onMapReady(p0: NaverMap) {
    }
}