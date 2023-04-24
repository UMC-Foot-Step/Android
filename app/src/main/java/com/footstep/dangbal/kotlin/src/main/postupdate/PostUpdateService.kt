package com.footstep.dangbal.kotlin.src.main.postupdate

import com.footstep.dangbal.kotlin.config.ApplicationClass
import com.footstep.dangbal.kotlin.src.main.postupdate.models.PostEditResponse
import com.footstep.dangbal.kotlin.src.main.postupdate.models.PostUpdateResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostUpdateService(val postUpdateActivityInterface: PostUpdateActivityInterface) {
    private val api = ApplicationClass.sRetrofit.create(PostUpdateRetrofitInterface::class.java)

    // api 연결 테스트
    fun getPostUpdateInfo(token: String, postingId: Int){
        // ApplicationClass.sSharedPreferences.edit().putString(ApplicationClass.X_ACCESS_TOKEN, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImZvb3RzdGVwQG5hdmVyLmNvbSIsImlhdCI6MTY3NDkxNDc2NiwiZXhwIjoxNjc1MjE3MTY2fQ.KxwX1Q0o-omU1rRIiUJBd9gLPbTRVciP_9g_sklW1Bk").apply()
        api.getPostUpdateInfo(postingId).enqueue(object :
            Callback<PostUpdateResponse> {
            override fun onResponse(
                call: Call<PostUpdateResponse>,
                response: Response<PostUpdateResponse>
            ) {
                postUpdateActivityInterface.onGetPostUpdateInfoSuccess(response.body() as PostUpdateResponse)
            }

            override fun onFailure(call: Call<PostUpdateResponse>, t: Throwable) {
                postUpdateActivityInterface.onGetPostUpdateInfoFailure(t.message ?: "통신 오류")
            }
            })
    }

    fun postPostUpdateInfo(token: String, image: MultipartBody.Part?, textHashMap: HashMap<String, RequestBody>, id: Int) {
        api.postPostUpdateInfo(image, textHashMap, id).enqueue(object:
        Callback<PostEditResponse> {
            override fun onResponse(
                call: Call<PostEditResponse>,
                response: Response<PostEditResponse>
            ) {
                postUpdateActivityInterface.onPostPostUpdateInfoSuccess(response.body() as PostEditResponse)
            }

            override fun onFailure(call: Call<PostEditResponse>, t: Throwable) {
                postUpdateActivityInterface.onPostPostUpdateInfoFailure(t.message ?: "통신 오류")
            }
        }

        )
    }
}