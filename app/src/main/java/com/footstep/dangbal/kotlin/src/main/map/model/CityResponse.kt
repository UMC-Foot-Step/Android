package com.footstep.dangbal.kotlin.src.main.map.model

import com.google.gson.annotations.SerializedName
import com.footstep.dangbal.kotlin.config.BaseResponse

data class CityResponse(
    @SerializedName("result") val result: ArrayList<ResultCityList>
):BaseResponse()
