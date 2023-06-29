package com.footstep.dangbal.kotlin.src.login

import android.content.Intent
import android.os.Bundle
import com.footstep.dangbal.kotlin.config.BaseActivity
import com.footstep.dangbal.kotlin.databinding.ActivityFindSuccessBinding

class FindSuccessActivity : BaseActivity<ActivityFindSuccessBinding>(ActivityFindSuccessBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding){
            nextBtn.setOnClickListener {
                val intent = Intent(this@FindSuccessActivity,LoginProcessActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}