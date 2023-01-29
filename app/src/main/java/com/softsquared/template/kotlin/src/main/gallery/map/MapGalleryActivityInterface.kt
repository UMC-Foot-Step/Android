package com.softsquared.template.kotlin.src.main.gallery.map

import com.softsquared.template.kotlin.src.main.gallery.info.models_sample.FeetStepInfoResponse
import com.softsquared.template.kotlin.src.main.gallery.models.PostListResponse
import com.softsquared.template.kotlin.src.main.gallery.models_sample.SectionModel

interface MapGalleryActivityInterface {

    fun onGetPostListByPositionSuccess(response: PostListResponse, mapGalleryActivityInterface: MapGalleryActivityInterface)

    fun onGetPostListByPositionFailure(response: String)

    fun changeGalleryInfoActivity(posting_id: Int)
}