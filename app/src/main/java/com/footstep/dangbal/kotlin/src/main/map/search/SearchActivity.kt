package com.footstep.dangbal.kotlin.src.main.map.search


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.footstep.dangbal.kotlin.config.ApplicationClass
import com.footstep.dangbal.kotlin.config.BaseActivity
import com.footstep.dangbal.kotlin.databinding.ActivitySearchBinding
import com.footstep.dangbal.kotlin.src.main.post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate){

    companion object{
        const val API_KEY = "KakaoAK 7a0040beeead54f63e4f70d4df6d8cae" //REST API 키
    }

    private val listItems = arrayListOf<PositionData>() // 리사이클러뷰 아이템
    private val listAdapter = ListAdapter(listItems)
    private var keyword = "" // 검색 키워드

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        Log.d("생명주기", "서치액티비티의 onCreate()")


        // 리사이클러뷰
        binding.searchPosRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.searchPosRv.adapter = listAdapter

        // 검색창 editText enter 이벤트
        setListenerEditText()

        // 리스트 아이템 클릭시
        listAdapter.setItemClickListener(object: ListAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                val item_result = listItems[position]
                val intent = Intent(binding.root.context, SearchResultActivity::class.java).apply{
                    putExtra("positionX", item_result.x)
                    putExtra("positionY", item_result.y)
                    putExtra("positionTitle", item_result.pos_title)

                    Log.d("dataxy", "SearchActivity's ${item_result.pos_title}: ${item_result.x}, ${item_result.y}")

                }
                startActivity(intent)

                if(!isFinishing) finish()
            }
        })

        // 검색 icon 눌렀을 때
        clickSearchIcon()

        // x버튼 누르면 뒤로 가기
        binding.searchPosIbQuit.setOnClickListener {
            finish()
        }

    }
    private fun setListenerEditText() {
        binding.searchPosEtPlace.setOnKeyListener{ view, keyCode, event ->
            // Enter Key Action
            if (event.action == KeyEvent.ACTION_DOWN
                && keyCode == KeyEvent.KEYCODE_ENTER) {
                // 키패드 내리기
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.searchPosEtPlace.windowToken, 0)
                // 검색 버튼 누르기
                keyword = binding.searchPosEtPlace.text.toString()
                searchKeyword(keyword)
                true
            }
            false
        }
    }

    // 검색 icon 눌렀을 때
    private fun clickSearchIcon() {
        binding.searchPosIv.setOnClickListener {
            keyword = binding.searchPosEtPlace.text.toString()
            searchKeyword(keyword)
        }
    }

    // 키워드 검색 함수
    private fun searchKeyword(keyword: String){
        val retrofit = ApplicationClass.kRetrofit
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
                Log.w("SearchActivity", "통신 실패: ${t.message}")
            }
        })
    }

    // 검색 결과 처리 함수
    private fun addItems(searchResult: ResultSearchKeyword?) {
        if(!searchResult?.documents.isNullOrEmpty()){
            // 검색 결과 있음
            listItems.clear()
            for (document in searchResult!!.documents) {
                // 도로명 주소가 있는 경우에만 검색
                if(!document.road_address_name.isNullOrEmpty()){
                    // 결과를 리사이클러 뷰에 추가
                    val item = PositionData(
                        document.place_name,
                        document.road_address_name,
                        document.x.toDouble(),
                        document.y.toDouble()
                    )
                    listItems.add(item)
                }
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