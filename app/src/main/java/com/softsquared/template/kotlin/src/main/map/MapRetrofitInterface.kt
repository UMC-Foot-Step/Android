package com.softsquared.template.kotlin.src.main.map

import com.softsquared.template.kotlin.src.main.map.model.AllResponse
import com.softsquared.template.kotlin.src.main.map.model.PopupResponse
import com.softsquared.template.kotlin.src.main.map.model.PopupResponse2
import retrofit2.Call
import retrofit2.http.*

interface MapRetrofitInterface {

    @GET("/footstep/all")
    fun getMapFootStepList() : Call<AllResponse>

    @GET("/footstep/{place_id}")
    fun getMapFootStepPopup(@Path("place_id") place_id : Int) : Call<PopupResponse>

}
