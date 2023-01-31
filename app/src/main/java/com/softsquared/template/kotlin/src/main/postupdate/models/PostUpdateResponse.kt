package com.softsquared.template.kotlin.src.main.postupdate.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class PostUpdateResponse (
    @SerializedName("result") val result: ArrayList<PostUpdateResult>
) : BaseResponse(), java.io.Serializable