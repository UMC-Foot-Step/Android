package com.softsquared.template.kotlin.src.main.myPage

import com.softsquared.template.kotlin.src.main.myPage.mypageResponseFile.MypageResponse
import com.softsquared.template.kotlin.src.main.myPage.mypageResultFile.Resultmypage

interface MyPageView {
    fun onMyPageSuccess(code : Int ,result: Resultmypage)
    fun onMyPageFailure(message:String)
}