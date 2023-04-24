package com.footstep.dangbal.kotlin.src.main.map.search

import com.footstep.dangbal.kotlin.src.main.map.model.PopupResponse2
import retrofit2.Call
import retrofit2.http.*

interface SearchRetrofitInterface {

    @GET("/footstep/{latitude}/{longitude}")
    fun getMapSearchFootStep(@Path("latitude") latitude : Double , @Path("longitude") longitude : Double) : Call<PopupResponse2>
}
