package com.softsquared.template.kotlin.src.signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnScrollChangeListener
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.ScrollView
import androidx.core.content.ContentProviderCompat.requireContext
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMainPostBinding
import com.softsquared.template.kotlin.databinding.ActivitySignupInfoBinding

class SignupInfoActivity : BaseActivity<ActivitySignupInfoBinding>(ActivitySignupInfoBinding::inflate) {


    var scroll1_flag = false
    var scroll2_flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var next = findViewById<Button>(R.id.next_btn)

        var scroll1 = findViewById<ScrollView>(R.id.scroll1)
        scroll1.setOnScrollChangeListener { view , i, i2, i3, i4 ->
            scroll1_flag = !view.canScrollVertically(1)
            if(scroll1_flag){
                checkscroll()
            }
        }


        var scroll2 = findViewById<ScrollView>(R.id.scroll2)
        scroll2.setOnScrollChangeListener { view , i, i2, i3, i4 ->
            scroll2_flag = !view.canScrollVertically(1)
            if(scroll2_flag){
                checkscroll()
            }
        }

        next.setOnClickListener {
            returncheckActivity()
        }

    }

    private fun returncheckActivity(){
        var intent = Intent(this,SignupCheckActivity::class.java)

        startActivity(intent)
    }

    private fun checkscroll(){

        if(scroll1_flag){
            binding.nextBtn.isEnabled = scroll2_flag
        }else {
            binding.nextBtn.isEnabled = false

        }

    }





}