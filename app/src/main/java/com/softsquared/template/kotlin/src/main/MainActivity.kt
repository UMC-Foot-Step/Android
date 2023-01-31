package com.softsquared.template.kotlin.src.main

import android.content.Intent
import android.os.Bundle
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMainBinding
import com.softsquared.template.kotlin.src.main.Example.HomeFragment
import com.softsquared.template.kotlin.src.main.feed.FeedFragment
import com.softsquared.template.kotlin.src.main.gallery.GalleryFragment
import com.softsquared.template.kotlin.src.main.map.MapFragment
import com.softsquared.template.kotlin.src.main.myPage.MyPageFragment
import com.softsquared.template.kotlin.src.main.post.PostActivity
import com.softsquared.template.kotlin.src.main.postupdate.PostUpdateActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.main_frm, MapFragment()).commitAllowingStateLoss()

        binding.mainBtmFab.setOnClickListener {
            val intent = Intent(this@MainActivity, PostUpdateActivity::class.java)
            startActivity(intent)
        }

        binding.mainBtmNav.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {

                    // 지도
                    R.id.menu_main_btm_nav_map -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, MapFragment())
                            .commitAllowingStateLoss()
                    }

                    // 갤러리 (GalleryService에서 더미데이터를 생성하고, GalleryFragment에 더미데이터 전달)
                    R.id.menu_main_btm_nav_gallary -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, GalleryFragment())
                            .commitAllowingStateLoss()
                    }

                    // 피드
                    R.id.menu_main_btm_nav_feed -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, FeedFragment())
                            .commitAllowingStateLoss()
                    }

                    // 마이페이지
                    R.id.menu_main_btm_nav_my_page -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, MyPageFragment())
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            selectedItemId = R.id.menu_main_btm_nav_map
        }
    }
}