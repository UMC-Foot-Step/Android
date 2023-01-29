package com.softsquared.template.kotlin.src.main.feed

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentFeedBinding
import com.softsquared.template.kotlin.src.main.gallery.info.GalleryInfoActivity
import com.softsquared.template.kotlin.src.main.gallery.map.MapGalleryActivity

class FeedFragment :
    BaseFragment<FragmentFeedBinding>(FragmentFeedBinding::bind, R.layout.fragment_feed) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /*
            MapGalleryActivity 전환 테스팅 코드
         */
        val intent = Intent(activity, MapGalleryActivity::class.java)
//        val bundle = Bundle()
//        bundle.putSerializable("feetStepInfoResponse", feetStepInfoResponse)
        intent.putExtra("placeId", 2)
        startActivity(intent)


    }
}