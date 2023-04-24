package com.footstep.dangbal.kotlin.src.main.map.search

import com.footstep.dangbal.kotlin.src.main.map.model.PopupResponse2

interface SearchResultActivityInterface {
    fun onGetMapSearchFootStepSuccess(response: PopupResponse2)

    fun onGetMapSearchFootStepFailure(message:String)
}