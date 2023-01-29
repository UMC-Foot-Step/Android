package com.softsquared.template.kotlin.src.main.map.search

import com.softsquared.template.kotlin.src.main.map.model.AllResponse
import com.softsquared.template.kotlin.src.main.map.model.PopupResponse
import com.softsquared.template.kotlin.src.main.map.model.PopupResponse2

interface SearchResultActivityInterface {
    fun onGetMapSearchFootStepSuccess(response: PopupResponse2)

    fun onGetMapSearchFootStepFailure(message:String)
}