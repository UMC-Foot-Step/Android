package com.softsquared.template.kotlin.src.main.gallery.map

import com.softsquared.template.kotlin.src.main.gallery.info.models.FeetStepInfoResponse
import com.softsquared.template.kotlin.src.main.gallery.models.SectionModel

interface MapGalleryActivityInterface {

    fun onGetPostListByPositionSuccess(response: ArrayList<SectionModel>, mapGalleryActivityInterface: MapGalleryActivityInterface)

    fun onGetPostListByPositionFailure(response: ArrayList<SectionModel>)

    fun changeGalleryInfoActivity(response: FeetStepInfoResponse)
}