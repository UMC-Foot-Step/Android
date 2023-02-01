package com.softsquared.template.kotlin.src.main.map.model

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class PopupResponse2(
    @SerializedName("result")val result:ResultPopupList2
):BaseResponse()
