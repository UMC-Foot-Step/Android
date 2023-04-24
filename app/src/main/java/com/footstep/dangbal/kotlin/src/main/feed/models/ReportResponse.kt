package com.footstep.dangbal.kotlin.src.main.feed.models

import com.google.gson.annotations.SerializedName
import com.footstep.dangbal.kotlin.config.BaseResponse

data class ReportResponse(
    @SerializedName("result") val result: String
):BaseResponse()
