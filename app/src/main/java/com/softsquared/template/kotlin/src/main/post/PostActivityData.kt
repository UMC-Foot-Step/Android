package com.softsquared.template.kotlin.src.main.post

import android.os.Parcelable

data class PostActivityData(
    val post_year : Int,
    val post_month : Int,
    val post_day : Int,
    val post_pic_url : String,
    val post_position_title : String,
    val post_title : String,
    val post_content : String,
    val post_open : Boolean
)
