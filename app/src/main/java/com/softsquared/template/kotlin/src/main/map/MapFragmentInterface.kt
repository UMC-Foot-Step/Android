package com.softsquared.template.kotlin.src.main.map

import com.naver.maps.map.overlay.Marker
import com.softsquared.template.kotlin.src.main.map.model.AllResponse
import com.softsquared.template.kotlin.src.main.map.model.PopupResponse

interface MapFragmentInterface {

    fun onGetMapFootStepListSuccess(response:AllResponse)

    fun onGetMapFootStepListFailure(message:String)

    fun onGetMapFootStepPopupSuccess(response: PopupResponse,placeId:Int)

    fun onGetMapFootStepPopupFailure(message:String)

}