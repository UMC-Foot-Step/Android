package com.footstep.dangbal.kotlin.src.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.footstep.dangbal.kotlin.R
import com.footstep.dangbal.kotlin.config.BaseActivity
import com.footstep.dangbal.kotlin.databinding.ActivityMainBinding
import com.footstep.dangbal.kotlin.src.main.feed.FeedListFragment
import com.footstep.dangbal.kotlin.src.main.gallery.GalleryFragment
import com.footstep.dangbal.kotlin.src.main.map.MapFragment
import com.footstep.dangbal.kotlin.src.main.myPage.MyPageFragment
import com.footstep.dangbal.kotlin.src.main.post.PostActivity


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private var time: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("생명주기", "메인의 onCreate()")

        var placeNameChk=false
        val extras=intent?.extras
        var placeName=""
        if(extras!=null) {
            placeName = extras!!["placeName"] as String
            Log.d("생명주기", "메인의 "+placeName)
            placeNameChk=true
        }
        //supportFragmentManager.beginTransaction().replace(R.id.main_frm, MapFragment()).commitAllowingStateLoss()

        binding.mainBtmFab.setOnClickListener {
            val intent = Intent(this@MainActivity, PostActivity::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        binding.mainBtmNav.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {

                    // 지도
                    R.id.menu_main_btm_nav_map -> {
                        //if (supportFragmentManager.findFragmentById(R.id.menu_main_btm_nav_map) != null){
                        Log.d("생명주기", "메인의 네비게이션바의"+placeName)

                        supportFragmentManager.beginTransaction()
                               //.show(MapFragment())
                                .replace(R.id.main_frm, MapFragment(placeName))
                                .commitAllowingStateLoss()
                        placeName = ""
                        placeNameChk=false
                        //}
                    }

                    // 갤러리 (GalleryService에서 더미데이터를 생성하고, GalleryFragment에 더미데이터 전달)
                    R.id.menu_main_btm_nav_gallary -> {
                        if(placeNameChk==true) {
                            placeName = ""
                            placeNameChk=false
                        }
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, GalleryFragment())
                            .commitAllowingStateLoss()
                    }

                    // 피드
                    R.id.menu_main_btm_nav_feed -> {
                        if(placeNameChk==true){
                            placeName=""
                            placeNameChk=false
                        }
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, FeedListFragment())
                            .commitAllowingStateLoss()
                    }

                    // 마이페이지
                    R.id.menu_main_btm_nav_my_page -> {
                        if(placeNameChk==true){
                            placeName=""
                            placeNameChk=false
                        }
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

    override fun onBackPressed() {
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis()
            showCustomToast("한번 더 누르면 종료됩니다")
        } else if (System.currentTimeMillis() - time < 2000) {
            finish()
        }
    }
}