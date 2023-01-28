package com.softsquared.template.kotlin.src.signup

import android.os.Bundle
import android.view.View
import android.view.View.OnScrollChangeListener
import android.widget.ScrollView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMainPostBinding

class SignupInfoActivity : BaseActivity<ActivityMainPostBinding>(ActivityMainPostBinding::inflate) {

    var position_flag = true
    var scroll1_flag = false
    var scroll2_flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_info)

        var scroll1 = findViewById<ScrollView>(R.id.scroll1)
        var scroll2 = findViewById<ScrollView>(R.id.scroll2)

        scroll1.setOnScrollChangeListener { View, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(position_flag){
                if(View.canScrollVertically(1)){
                    scroll1_flag = true
                }
                position_flag = false
            }
            position_flag = true

        }

        scroll2.setOnScrollChangeListener { View, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(position_flag){
                scroll2_flag = View.canScrollVertically(1)
                position_flag = false
            }
            position_flag = true
            scroll2_flag = false

        }
    }
}