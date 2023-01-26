package com.softsquared.template.kotlin.src.main.gallery.map

import com.softsquared.template.kotlin.src.main.gallery.info.models_sample.FeetStepInfoResponse
import com.softsquared.template.kotlin.src.main.gallery.models_sample.SectionModel

interface MapGalleryActivityInterface {

    fun onGetPostListByPositionSuccess(response: ArrayList<SectionModel>, mapGalleryActivityInterface: MapGalleryActivityInterface)

    fun onGetPostListByPositionFailure(response: ArrayList<SectionModel>)

    fun changeGalleryInfoActivity(response: FeetStepInfoResponse)
}