package com.softsquared.template.kotlin.src.main.feed

import android.os.Bundle
import android.view.View
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentFeedBinding

class FeedFragment :
    BaseFragment<FragmentFeedBinding>(FragmentFeedBinding::bind, R.layout.fragment_feed) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}