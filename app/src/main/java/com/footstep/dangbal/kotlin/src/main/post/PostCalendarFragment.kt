package com.footstep.dangbal.kotlin.src.main.post

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.footstep.dangbal.kotlin.R
import com.footstep.dangbal.kotlin.config.BaseFragment
import com.footstep.dangbal.kotlin.databinding.FragmentPostCalendarBinding

class PostCalendarFragment : BaseFragment<FragmentPostCalendarBinding>(FragmentPostCalendarBinding::bind, R.layout.fragment_post_calendar) {
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}