package com.softsquared.template.kotlin.src.main.map.area

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityAreaBinding
import com.softsquared.template.kotlin.src.main.MainActivity

class AreaActivity : BaseActivity<ActivityAreaBinding>(ActivityAreaBinding::inflate)
{
    var placeName=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent=intent
        initButtonView(intent)
    }

    private fun initButtonView(intent : Intent) {
        binding.seoul.setOnClickListener {
            placeName = "서울"
            Log.d("FootStepList","AreaActivity  "+placeName)

        }

        binding.incheon.setOnClickListener {
            placeName = "인천"
        }

        binding.busan.setOnClickListener {
            placeName= "부산"
        }

        binding.daegu.setOnClickListener {
            placeName= "대구"
        }

        binding.daejeon.setOnClickListener {
            placeName= "대전"
        }

        binding.gwangju.setOnClickListener {
            placeName= "광주"
        }

        binding.ulsan.setOnClickListener {
            placeName= "울산"
        }

        binding.sejong.setOnClickListener {
            placeName= "세종특별자치시"
        }

        binding.gyeonggi.setOnClickListener {
            placeName= "경기"
        }

        binding.gangwon.setOnClickListener {
            placeName= "강원"
        }

        binding.chungcheongbuk.setOnClickListener {
            placeName= "충북"
        }

        binding.chungcheongnam.setOnClickListener {
            placeName= "충남"
        }

        binding.gyeongsangbuk.setOnClickListener {
            placeName= "경북"
        }
        binding.gyeongsangnam.setOnClickListener {
            placeName= "경남"
        }

        binding.jeollabuk.setOnClickListener {
            placeName= "전북"
        }

        binding.jeollanam.setOnClickListener {
            placeName= "전남"
        }

        binding.jeju.setOnClickListener {
            placeName= "제주특별자치도"
        }

        binding.cityBtn.setOnClickListener {
            val intent=Intent(this, MainActivity::class.java).apply {
                putExtra("placeName", placeName)
            }
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("생명주기","AreaActivity onDestroy() "+placeName)
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