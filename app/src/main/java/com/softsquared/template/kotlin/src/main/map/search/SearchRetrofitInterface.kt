package com.softsquared.template.kotlin.src.main.map.search

import com.softsquared.template.kotlin.src.main.map.model.AllResponse
import com.softsquared.template.kotlin.src.main.map.model.PopupResponse
import com.softsquared.template.kotlin.src.main.map.model.PopupResponse2
import retrofit2.Call
import retrofit2.http.*

interface SearchRetrofitInterface {

    @GET("/footstep/{latitude}/{longitude}")
    fun getMapSearchFootStep(@Path("latitude") latitude : Double , @Path("longitude") longitude : Double) : Call<PopupResponse2>
}
