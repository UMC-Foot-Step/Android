package com.softsquared.template.kotlin.src.main.post

data class PositionData(
    val pos_title : String,
    val pos_address : String,
    // 필요하면 사용하기
    val x: Double, // 경도(Longitude)
    val y: Double // 위도(Latitude)
)
