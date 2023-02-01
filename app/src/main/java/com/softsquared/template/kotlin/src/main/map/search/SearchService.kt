package com.softsquared.template.kotlin.src.main.map.search

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.softsquared.template.kotlin.src.main.map.MapFragmentInterface
import com.softsquared.template.kotlin.src.main.map.MapRetrofitInterface
import com.softsquared.template.kotlin.src.main.map.model.AllResponse
import com.softsquared.template.kotlin.src.main.map.model.PopupResponse
import com.softsquared.template.kotlin.src.main.map.model.PopupResponse2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchService(val searchResultActivityInterface: SearchResultActivityInterface) {
    val accessToken="Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImZvb3RzdGVwQG5hdmVyLmNvbSIsImlhdCI6MTY3NDkxNDc2NiwiZXhwIjoxNjc1MjE3MTY2fQ.KxwX1Q0o-omU1rRIiUJBd9gLPbTRVciP_9g_sklW1Bk"

    fun tryGetMapSearchFootStep(latitude : Double,longitude : Double){
        ApplicationClass.sSharedPreferences.edit().putString(X_ACCESS_TOKEN, accessToken).apply()

        val searchRetrofitInterface=ApplicationClass.sRetrofit.create(SearchRetrofitInterface::class.java)
        searchRetrofitInterface.getMapSearchFootStep(latitude,longitude).enqueue(object : Callback<PopupResponse2>{
            override fun onResponse(
                call: Call<PopupResponse2>,
                response: Response<PopupResponse2>
            ) {
                Log.d("FootStepList", "tryGetMapSearchFootStep 되긴하니?")

                if(response.body()!=null) {
                    Log.d("FootStepList", "서치서비스에서의 결과1 : ${response.body().toString()}")
                    searchResultActivityInterface.onGetMapSearchFootStepSuccess(response.body() as PopupResponse2)
                }
                else
                    Log.d("FootStepList", "서치서비스에서의 결과2 : ${response.body().toString()}")            }

            override fun onFailure(call: Call<PopupResponse2>, t: Throwable) {
                searchResultActivityInterface.onGetMapSearchFootStepFailure(t.message ?: "통신 오류")
            }
        })
    }

}