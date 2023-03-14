package com.softsquared.template.kotlin.src.main.feed.models

import com.google.gson.annotations.SerializedName

data class createReportDto(
    @SerializedName(value = "reasonNumber")val reasonNumber: Int,
    @SerializedName(value = "targetNumber")val targetNumber: Int
    )
