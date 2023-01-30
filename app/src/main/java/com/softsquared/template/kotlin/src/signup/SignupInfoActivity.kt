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
        setContentView(R.layout.activity_signup_info)

        var next = findViewById<Button>(R.id.next_btn)

        next.isClickable = false
        next.setBackgroundColor(R.drawable.login_button)

        var scroll1 = findViewById<ScrollView>(R.id.scroll1)
        scroll1.setOnScrollChangeListener { view , i, i2, i3, i4 ->
            scroll1_flag = !view.canScrollVertically(1)
        }


        var scroll2 = findViewById<ScrollView>(R.id.scroll2)
        scroll2.setOnScrollChangeListener { view , i, i2, i3, i4 ->
            scroll2_flag = !view.canScrollVertically(1)
        }

        //정신 나가기 일보 직전 나중에 해결할 예정 ㅠㅠ

        if(scroll1_flag){
            if(scroll2_flag){
                binding.nextBtn.isClickable = true
                next.setBackgroundColor(R.drawable.solid_button)
            }
            else{
                binding.nextBtn.isClickable = false
                next.setBackgroundColor(R.drawable.login_button)
            }
        }else {
            binding.nextBtn.isClickable = false
            next.setBackgroundColor(R.drawable.login_button)
        }



        next.setOnClickListener {
            Log.d("Tester", "onCreate: $scroll2_flag,$scroll1_flag")
            returncheckActivity()

        }

    }

    private fun returncheckActivity(){
        var intent = Intent(this,SignupCheckActivity::class.java)
        startActivity(intent)
        finish()
    }





}