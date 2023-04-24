package com.footstep.dangbal.kotlin.src.main.gallery.map

import com.footstep.dangbal.kotlin.src.main.gallery.models.PostListResponse

interface MapGalleryActivityInterface {

    fun onGetPostListByPositionSuccess(response: PostListResponse, mapGalleryActivityInterface: MapGalleryActivityInterface)

    fun onGetPostListByPositionFailure(response: String)

    fun changeGalleryInfoActivity(posting_id: Int)
}