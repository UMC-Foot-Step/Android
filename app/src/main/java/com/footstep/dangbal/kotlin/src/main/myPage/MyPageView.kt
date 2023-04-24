package com.footstep.dangbal.kotlin.src.main.myPage

import com.footstep.dangbal.kotlin.src.main.myPage.mypageResultFile.Resultmypage

interface MyPageView {
    fun onMyPageSuccess(code : Int ,result: Resultmypage)
    fun onMyPageFailure(message:String)
}