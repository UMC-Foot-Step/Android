package com.softsquared.template.kotlin.src.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMainBinding
import com.softsquared.template.kotlin.src.main.feed.FeedListFragment
import com.softsquared.template.kotlin.src.main.gallery.GalleryFragment
import com.softsquared.template.kotlin.src.main.map.MapFragment
import com.softsquared.template.kotlin.src.main.myPage.MyPageFragment
import com.softsquared.template.kotlin.src.main.post.PostActivity


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("생명주기", "메인의 onCreate()")

        var placeNameChk=false
        val extras=intent?.extras
        var placeName=""
        if(extras!=null) {
            placeName = extras!!["placeName"] as String
            Log.d("FootStepList", "메인의 "+placeName)
            placeNameChk=true
        }
            //supportFragmentManager.beginTransaction().replace(R.id.main_frm, MapFragment()).commitAllowingStateLoss()

        binding.mainBtmFab.setOnClickListener {
            val intent = Intent(this@MainActivity, PostActivity::class.java)
            startActivity(intent)
        }

        binding.mainBtmNav.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {

                    // 지도
                    R.id.menu_main_btm_nav_map -> {
                        //if (supportFragmentManager.findFragmentById(R.id.menu_main_btm_nav_map) != null){
                            supportFragmentManager.beginTransaction()
                               //.show(MapFragment())
                                .replace(R.id.main_frm, MapFragment(placeName))
                                .commitAllowingStateLoss()
                        //}
                    }

                    // 갤러리 (GalleryService에서 더미데이터를 생성하고, GalleryFragment에 더미데이터 전달)
                    R.id.menu_main_btm_nav_gallary -> {
                        if(placeNameChk==true)
                            placeName=""
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, GalleryFragment())
                            .commitAllowingStateLoss()
                    }

                    // 피드
                    R.id.menu_main_btm_nav_feed -> {
                        if(placeNameChk==true)
                            placeName=""
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, FeedListFragment())
                            .commitAllowingStateLoss()
                    }

                    // 마이페이지
                    R.id.menu_main_btm_nav_my_page -> {
                        if(placeNameChk==true)
                            placeName=""
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

    override fun onDestroy() {
        Log.d("생명주기", "메인의 onDestroy()")
        super.onDestroy()
    }

    override fun onStart() {
        Log.d("생명주기", "메인의 onStart()")

        super.onStart()
    }

    override fun onResume() {
        Log.d("생명주기", "메인의 onResume()")

        super.onResume()
    }
}