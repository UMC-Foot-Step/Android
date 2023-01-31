package com.softsquared.template.kotlin.src.main.postupdate

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.postupdate.models.PostUpdateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostUpdateService(val postUpdateActivityInterface: PostUpdateActivityInterface) {
    // api 연결 테스트
    fun getPostUpdateInfo(postingId: Int){
        ApplicationClass.sSharedPreferences.edit().putString(ApplicationClass.X_ACCESS_TOKEN, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImZvb3RzdGVwQG5hdmVyLmNvbSIsImlhdCI6MTY3NDkxNDc2NiwiZXhwIjoxNjc1MjE3MTY2fQ.KxwX1Q0o-omU1rRIiUJBd9gLPbTRVciP_9g_sklW1Bk").apply()
        val postUpdateRetrofitInterface = ApplicationClass.sRetrofit.create(PostUpdateRetrofitInterface::class.java)
        postUpdateRetrofitInterface.getPostUpdateInfo(postingId).enqueue(object :
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
}