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
            binding.seoul.isSelected = !binding.seoul.isSelected
            placeName="서울"
            Log.d("FootStepList","AreaActivity  "+placeName)

        }

        binding.incheon.setOnClickListener {
            binding.incheon.isSelected=!binding.incheon.isSelected
            placeName="인천"
        }

        binding.busan.setOnClickListener {
            binding.busan.isSelected=!binding.busan.isSelected
            placeName="부산"
        }

        binding.daegu.setOnClickListener {
            binding.daegu.isSelected=!binding.daegu.isSelected
            placeName="대구"
        }

        binding.daejeon.setOnClickListener {
            binding.daejeon.isSelected=!binding.daejeon.isSelected
            placeName="대전"
        }

        binding.gwangju.setOnClickListener {
            binding.gwangju.isSelected=!binding.gwangju.isSelected
            placeName="광주"
        }

        binding.ulsan.setOnClickListener {
            binding.ulsan.isSelected=!binding.ulsan.isSelected
            placeName="울산"
        }

        binding.sejong.setOnClickListener {
            binding.sejong.isSelected=!binding.sejong.isSelected
            placeName="세종특별자치시"
        }

        binding.gyeonggi.setOnClickListener {
            binding.gyeonggi.isSelected=!binding.gyeonggi.isSelected
            placeName="경기"
        }

        binding.gangwon.setOnClickListener {
            binding.gangwon.isSelected=!binding.gangwon.isSelected
            placeName="강원"
        }

        binding.chungcheongbuk.setOnClickListener {
            binding.chungcheongbuk.isSelected=!binding.chungcheongbuk.isSelected
            placeName="충북"
        }

        binding.chungcheongnam.setOnClickListener {
            binding.chungcheongnam.isSelected=!binding.chungcheongnam.isSelected
            placeName="충남"
        }

        binding.gyeongsangbuk.setOnClickListener {
            binding.gyeongsangbuk.isSelected=!binding.gyeongsangbuk.isSelected
            placeName="경북"
        }
        binding.gyeongsangnam.setOnClickListener {
            binding.gyeongsangnam.isSelected=!binding.gyeongsangnam.isSelected
            placeName="경남"
        }

        binding.jeollabuk.setOnClickListener {
            binding.jeollabuk.isSelected=!binding.jeollabuk.isSelected
            placeName="전북"
        }

        binding.jeollanam.setOnClickListener {
            binding.jeollanam.isSelected=!binding.jeollanam.isSelected
            placeName="전남"
        }

        binding.jeju.setOnClickListener {
            binding.jeju.isSelected=!binding.jeju.isSelected
            placeName="제주특별자치도"
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