package com.footstep.dangbal.kotlin.src.main.map.model

import com.google.gson.annotations.SerializedName
import com.footstep.dangbal.kotlin.config.BaseResponse

data class PopupResponse(
    @SerializedName("result")val result:ResultPopupList
):BaseResponse()
