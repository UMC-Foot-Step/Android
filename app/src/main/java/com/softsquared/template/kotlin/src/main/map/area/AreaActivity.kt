package com.softsquared.template.kotlin.src.main.map.area

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityAreaBinding

class AreaActivity : BaseActivity<ActivityAreaBinding>(ActivityAreaBinding::inflate)
{
    lateinit var placeName:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent=intent
        initButtonView(intent)
    }

    private fun initButtonView(intent : Intent) {
        binding.seoul.setOnClickListener {
            binding.seoul.isSelected = !binding.seoul.isSelected
            placeName="서울"
        }
        binding.incheon.setOnClickListener {
            binding.incheon.isSelected=!binding.incheon.isSelected
        }
        binding.busan.setOnClickListener {
            binding.busan.isSelected=!binding.busan.isSelected
        }
        binding.daegu.setOnClickListener {
            binding.daegu.isSelected=!binding.daegu.isSelected
        }
        binding.daejeon.setOnClickListener {
            binding.daejeon.isSelected=!binding.daejeon.isSelected
        }
        binding.gwangju.setOnClickListener {
            binding.gwangju.isSelected=!binding.gwangju.isSelected
        }
        binding.ulsan.setOnClickListener {
            binding.ulsan.isSelected=!binding.ulsan.isSelected
        }
        binding.sejong.setOnClickListener {
            binding.sejong.isSelected=!binding.sejong.isSelected
        }
        binding.gyeonggi.setOnClickListener {
            binding.gyeonggi.isSelected=!binding.gyeonggi.isSelected
        }
        binding.gangwon.setOnClickListener {
            binding.gangwon.isSelected=!binding.gangwon.isSelected
        }
        binding.chungcheongbuk.setOnClickListener {
            binding.chungcheongbuk.isSelected=!binding.chungcheongbuk.isSelected
        }
        binding.chungcheongnam.setOnClickListener {
            binding.chungcheongnam.isSelected=!binding.chungcheongnam.isSelected
        }
        binding.gyeongsangbuk.setOnClickListener {
            binding.gyeongsangbuk.isSelected=!binding.gyeongsangbuk.isSelected
        }
        binding.gyeongsangnam.setOnClickListener {
            binding.gyeongsangnam.isSelected=!binding.gyeongsangnam.isSelected
        }
        binding.jeollabuk.setOnClickListener {
            binding.jeollabuk.isSelected=!binding.jeollabuk.isSelected
        }
        binding.jeollanam.setOnClickListener {
            binding.jeollanam.isSelected=!binding.jeollanam.isSelected
        }
        binding.jeju.setOnClickListener {
            binding.jeju.isSelected=!binding.jeju.isSelected
        }
        binding.cityBtn.setOnClickListener {
            intent.putExtra("placeName",placeName)
            finish()
        }
    }
}

/* binding.seoul.background =
                ContextCompat.getDrawable(this, R.drawable.city_check)

            binding.seoul.setTextColor(ContextCompat.getColor(this,R.color.white))
            */
/*
               binding.seoul.background =
                   ContextCompat.getDrawable(this, R.drawable.city_uncheck)

               binding.seoul.setTextColor(ContextCompat.getColor(this,R.color.orange))

                */