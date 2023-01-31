package com.softsquared.template.kotlin.src.main.gallery.info.models_sample

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResultCommentList(

    @SerializedName("username") val username: String,
    @SerializedName("des") val des: String
) : Serializable
