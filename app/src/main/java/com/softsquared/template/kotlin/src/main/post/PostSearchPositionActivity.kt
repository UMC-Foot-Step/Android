package com.softsquared.template.kotlin.src.main.post

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.databinding.ActivitySearchPositionBinding
import com.softsquared.template.kotlin.config.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    companion object{
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK 7a0040beeead54f63e4f70d4df6d8cae" //REST API 키
    }

    private val listItems = arrayListOf<PositionData>() // 리사이클러뷰 아이템
    private val listAdapter = ListAdapter(listItems)
    private var keyword = "" // 검색 키워드

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root

        // 리사이클러뷰
        binding.searchPosRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.searchPosRv.adapter = listAdapter

        // 리스트 아이템 클릭시
        listAdapter.setItemClickListener(object: ListAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                val item_result = listItems[position]
                val intent = Intent(binding.root.context, PostActivity::class.java).apply{
                    putExtra("positionTitle", item_result.pos_title)
                }
                setResult(RESULT_OK, intent)
                if(!isFinishing) finish()
            }
        })

        // 검색 icon 눌렀을 때
        binding.searchPosIv.setOnClickListener {
            keyword = binding.searchPosEtPlace.text.toString()
            searchKeyword(keyword)

        }



        // x버튼 누르면 뒤로 가기
        binding.searchPosIbQuit.setOnClickListener {
            finish()
        }



    }
    // 키워드 검색 함수
    private fun searchKeyword(keyword: String){
        val retrofit = Retrofit.Builder() // Retrofit 구성
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPI::class.java) // 통신 인터페이스를 객체로 생성
        val call = api.getSearchKeyword(API_KEY, keyword) // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(object: Callback<ResultSearchKeyword> {
            override fun onResponse(
                call: Call<ResultSearchKeyword>,
                response: Response<ResultSearchKeyword>
            ) {
                // 통신 성공 (검색 결과는 response.body()에 담겨있음)
                // log는 확인용으로 살려놨음
                Log.d("Test", "Raw: ${response.raw()}")
                Log.d("Test", "Body: ${response.body()}")
                addItems(response.body())
            }

            override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
                // 통신 실패
                Log.w("PostSearchPositionActivity", "통신 실패: ${t.message}")
            }
        })
    }

    // 검색 결과 처리 함수
    private fun addItems(searchResult: ResultSearchKeyword?) {
        if(!searchResult?.documents.isNullOrEmpty()){
            // 검색 결과 있음
            listItems.clear()
            for (document in searchResult!!.documents) {
                // 결과를 리사이클러 뷰에 추가
                val item = PositionData(
                    document.place_name,
                    document.road_address_name,
                    document.x.toDouble(),
                    document.y.toDouble()
                )
                listItems.add(item)
            }
            listAdapter.notifyDataSetChanged()
            binding.searchPosTvResultCount.text = listItems.size.toString()
        }
        else{
            // 검색 결과 없음
            Toast.makeText(this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

}