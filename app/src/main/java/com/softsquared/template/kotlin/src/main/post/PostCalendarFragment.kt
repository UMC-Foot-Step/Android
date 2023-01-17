package com.softsquared.template.kotlin.src.main.post

import android.content.Context
import android.media.audiofx.Visualizer.OnDataCaptureListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentPostCalendarBinding

interface OnDatePickerSetListener{
    fun onDatePickerSet(year: Int, month: Int, day: Int)
}

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