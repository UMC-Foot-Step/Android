package com.softsquared.template.kotlin.src.main.post

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.softsquared.template.kotlin.src.main.post.models.PostListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 메인 화면 이벤트에 따른 API 요청을 보내는 서비스 클래스
// 네트워크 관련
/*
    백엔드 API를 엮기 전인 초기 프론트 리소스가지고 작업할 땐,
    서비스에서 더미 조회데이터를 만들어서 메인화면에 전달한다.

    백엔드 API 엮을 때는
    서비스에서 조회 API 요청을 보낸다. (Retrofit 객체 생성)
 */
class PostService {

    // API post
    fun postPost(image: MultipartBody.Part?, textHashMap: HashMap<String, RequestBody>){
        ApplicationClass.sSharedPreferences.edit().putString(X_ACCESS_TOKEN, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImZvb3RzdGVwQG5hdmVyLmNvbSIsImlhdCI6MTY3NDY2MDA5MSwiZXhwIjoxNjc0OTYyNDkxfQ.W7MNMFI43SPbcw5pLhpbsuic0_nCDRcqHKPgEipV9ko")
        val postHashMap = hashMapOf<String, RequestBody>()

        val postRetrofitInterface = ApplicationClass.sRetrofit.create(PostRetrofitInterface::class.java)
        postRetrofitInterface.postPostList(image, postHashMap)
            .enqueue(object : Callback<PostListResponse>{
                override fun onResponse(
                    call: Call<PostListResponse>,
                    response: Response<PostListResponse>
                ) {
                    val result = response.body()
                    if(response.isSuccessful){
                        Log.d("post 성공", "$result")
                        Log.d("image", "$image")
                        Log.d("postHashMap", "$postHashMap")
                    }
                    else{
                        Log.d("post 실패", "$result")
                        Log.d("image", "$image")
                        Log.d("postHashMap", "$postHashMap")
                    }
                }

                override fun onFailure(call: Call<PostListResponse>, t: Throwable) {
                    Log.d("서버연결 실패", t.message.toString())
                }
            })
    }
}