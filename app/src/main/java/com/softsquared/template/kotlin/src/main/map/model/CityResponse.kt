package com.softsquared.template.kotlin.src.main.map.model

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class CityResponse(
    @SerializedName("result") val result: ArrayList<ResultCityList>
):BaseResponse()
