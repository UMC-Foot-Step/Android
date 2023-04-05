package com.softsquared.template.kotlin.src.main.post

import android.util.Log
import com.naver.maps.map.overlay.Marker
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.UserCode
import com.softsquared.template.kotlin.src.main.map.MapRetrofitInterface
import com.softsquared.template.kotlin.src.main.map.model.AllResponse
import com.softsquared.template.kotlin.src.main.post.models.PostResponse
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
class PostService(val postActivityInterface: PostActivityInterface) {
    private val api = ApplicationClass.sRetrofit.create(PostRetrofitInterface::class.java)
    // API text post
    /*
    fun postInfo(token: String, image: MultipartBody.Part?, textHashMap: HashMap<String, RequestBody>){
        api.postInfoList(image, textHashMap).enqueue(object:
        Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                postActivityInterface.onPostPostInfoSuccess(response.body() as PostResponse)
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                postActivityInterface.onPostPostInfoFailure(t.message ?: "통신 오류")
            }
        }
        )
    }
     */
    suspend fun postInfo(token: String, image: MultipartBody.Part?, textHashMap: HashMap<String, RequestBody>){
        try{
            val response=api.postInfoList(image, textHashMap)
            Log.d("데이터 로드", "postInfo 되긴하니?")
            if(response!=null)
                postActivityInterface.onPostPostInfoSuccess(response)
            else
                Log.d("데이터 로드", "postInfo에서의 결과 : ${response.toString()}")
        }
        catch (e:Exception){
            Log.d("데이터 로드", "postInfotFailure "+e.message.toString())
            postActivityInterface.onPostPostInfoFailure(e.message ?: "통신 오류")

        }
    }

}