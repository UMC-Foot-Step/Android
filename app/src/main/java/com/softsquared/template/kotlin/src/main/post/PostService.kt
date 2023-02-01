package com.softsquared.template.kotlin.src.main.post

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.softsquared.template.kotlin.config.UserCode
import com.softsquared.template.kotlin.src.main.post.models.PostListResponse
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
    // API text post
    fun postText(hashMap: HashMap<String, Any>){
        // jwt 삭제
//        removeJwt()
//        ApplicationClass.sSharedPreferences.edit().putString(X_ACCESS_TOKEN, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImZvb3RzdGVwQG5hdmVyLmNvbSIsImlhdCI6MTY3NDkxNDc2NiwiZXhwIjoxNjc1MjE3MTY2fQ.KxwX1Q0o-omU1rRIiUJBd9gLPbTRVciP_9g_sklW1Bk").apply()

        val postRetrofitInterface = ApplicationClass.sRetrofit.create(PostRetrofitInterface::class.java)
        postRetrofitInterface.postTextList(hashMap)
            .enqueue(object : Callback<PostListResponse>{
                override fun onResponse(
                    call: Call<PostListResponse>,
                    response: Response<PostListResponse>
                ) {
                    val result = response.body()
                    if(response.isSuccessful){
                        Log.d("posttext 성공", "$result")
                        Log.d("value", hashMap.toString())
                    }
                    else{
                        Log.d("value", hashMap.toString())
                    }
                }

                override fun onFailure(call: Call<PostListResponse>, t: Throwable) {
                    Log.d("서버연결 실패", t.message.toString())
                }

            })
    }

    fun removeJwt(){
        val spf = ApplicationClass.sSharedPreferences
        val editor = spf.edit()

        editor.remove(UserCode.jwt)
        editor.apply()
    }
    /*
    fun postPost(image: MultipartBody.Part?, content: MultipartBody.Part, date: MultipartBody.Part,
    title: MultipartBody.Part, open: MultipartBody.Part, hashMap: HashMap<String, RequestBody>){
        ApplicationClass.sSharedPreferences.edit().putString(X_ACCESS_TOKEN, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImZvb3RzdGVwQG5hdmVyLmNvbSIsImlhdCI6MTY3NDY2MDA5MSwiZXhwIjoxNjc0OTYyNDkxfQ.W7MNMFI43SPbcw5pLhpbsuic0_nCDRcqHKPgEipV9ko")

        val postRetrofitInterface = ApplicationClass.sRetrofit.create(PostRetrofitInterface::class.java)
        postRetrofitInterface.postPostList(image, content, date, title, open, hashMap)
            .enqueue(object : Callback<PostListResponse>{
                override fun onResponse(
                    call: Call<PostListResponse>,
                    response: Response<PostListResponse>
                ) {
                    val result = response.body()
                    if(response.isSuccessful){
                        Log.d("post 성공", "$result")
                        Log.d("image", "$image")
                    }
                    else{
                        Log.d("post 실패", "$result")
                        Log.d("image", "$image")
                        Log.d("content", "$content")
                        Log.d("date", "$date")
                        Log.d("title", "$title")
                        Log.d("open", "$open")
                        Log.d("hashMap", "$hashMap")
                    }
                }

                override fun onFailure(call: Call<PostListResponse>, t: Throwable) {
                    Log.d("서버연결 실패", t.message.toString())
                }
            })
    }
    */
    // API post

}