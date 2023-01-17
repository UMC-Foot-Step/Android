package com.softsquared.template.kotlin.src.main.post

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.databinding.ActivitySearchPositionBinding
import com.softsquared.template.kotlin.config.BaseActivity

class PostSearchPositionActivity : BaseActivity<ActivitySearchPositionBinding>(ActivitySearchPositionBinding::inflate){
    // adapter 객체 생성
    //private lateinit var adapter: PostSearchPositionRecyclerViewAdapter
    // private val positionDatas = mutableListOf<PostSearchPositionData>()
    // private lateinit var adapter : PositionListAdapter
    //private val itemList : List<PositionData> = listOf(
        //PositionData("투썸플레이스 인천인하대후문점", "인천 미추홀구 인하로 75 2-3층", 23),
        //PositionData("투썸플레이스 인천독정이삼거리점", "인천 미추홀구 독정이로 3", 100),
        //PositionData("투썸플레이스 인천숭의점", "인천 미추홀구 인주대로 51", 123),
        //PositionData("투썸플레이스인천용현점", "인천 미추홀구 아암대로 87 서해관광호텔", 200)
    //)
    var positionDatas = mutableListOf<PositionData>()
    private val adapter : PositionListAdapter by lazy{
        PositionListAdapter(positionDatas)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // x버튼 누르면 뒤로 가기
        binding.searchPosIbQuit.setOnClickListener {
            finish()
        }

        adapter.setItemClickListener(object : PositionListAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                val item = positionDatas[position]
                val intent = Intent(binding.root.context, PostActivity::class.java)
                intent.putExtra("positionTitle", item.pos_title)
                startActivity(intent)
                finish()
            }
        })

        // recyclerView
        initializePositionlist()
        initPositionRecyclerView()

        binding.searchPosTvResultCount.text = positionDatas.size.toString()

    }
    private fun initPositionRecyclerView(){
        //val adapter = PostSearchPositionRecyclerViewAdapter()
        //adapter.positionDataList = positionDatas
        //binding.searchPosRv.adapter = adapter
        //binding.searchPosRv.layoutManager = LinearLayoutManager(this)
        adapter.itemList = positionDatas
        binding.searchPosRv.adapter=adapter
        binding.searchPosRv.layoutManager = LinearLayoutManager(this)
    }

    private fun initializePositionlist(){
        with(positionDatas){
            add(PositionData("투썸플레이스 인천인하대후문점", "인천 미추홀구 인하로 75 2-3층", 23))
            add(PositionData("투썸플레이스 인천독정이삼거리점", "인천 미추홀구 독정이로 3", 100))
            add(PositionData("투썸플레이스 인천숭의점", "인천 미추홀구 인주대로 51", 123))
            add(PositionData("투썸플레이스인천용현점", "인천 미추홀구 아암대로 87 서해관광호텔", 200))
        }
    }

}