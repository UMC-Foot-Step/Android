package com.softsquared.template.kotlin.src.main.map

import com.softsquared.template.kotlin.src.main.map.model.AllResponse
import com.softsquared.template.kotlin.src.main.map.model.PopupResponse
import com.softsquared.template.kotlin.src.main.map.model.PopupResponse2
import com.softsquared.template.kotlin.src.main.map.model.SpecificFstResponse
import retrofit2.Call
import retrofit2.http.*

interface MapRetrofitInterface {

    @GET("/footstep/all")
    suspend fun getMapFootStepList() : AllResponse

    @GET("/footstep/{place-id}")
    suspend fun getMapFootStepPopup(@Path("place-id") place_id : Int) : PopupResponse

    @GET("/footstep/specific/{start-date}/{end-date}")
    suspend fun getMapFootStepSpecific(@Path("start-date") start_date : String, @Path("end-date") end_date:String) : SpecificFstResponse

    @GET("/footstep/city/{city-name}")
    suspend fun getMapFootStepCity(@Path("city-name")city: String) : AllResponse

}
