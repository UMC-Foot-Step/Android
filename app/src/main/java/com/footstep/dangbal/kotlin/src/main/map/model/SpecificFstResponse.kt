package com.footstep.dangbal.kotlin.src.main.map.model

import com.google.gson.annotations.SerializedName
import com.footstep.dangbal.kotlin.config.BaseResponse

data class SpecificFstResponse(
    @SerializedName("result") val result: allPlaceDto
):BaseResponse()
