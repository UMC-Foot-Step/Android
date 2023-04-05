package com.softsquared.template.kotlin.src.main.map

import com.naver.maps.map.overlay.Marker
import com.softsquared.template.kotlin.src.main.map.model.AllResponse
import com.softsquared.template.kotlin.src.main.map.model.CityResponse
import com.softsquared.template.kotlin.src.main.map.model.PopupResponse
import com.softsquared.template.kotlin.src.main.map.model.SpecificFstResponse

interface MapFragmentInterface {

    suspend fun onGetMapFootStepListSuccess(response:AllResponse)

    suspend fun onGetMapFootStepListFailure(message:String)


    suspend fun onGetMapFootStepPopupSuccess(response: PopupResponse,placeId:Int)

    suspend fun onGetMapFootStepPopupFailure(message:String)


    suspend fun onGetMapFootStepSpecificSuccess(response:SpecificFstResponse)

    suspend fun onGetMapFootStepSpecificFailure(message:String)


    suspend fun onGetMapFootStepCitySuccess(response: CityResponse)

    suspend fun onGetMapFootStepCityFailure(message:String)

}