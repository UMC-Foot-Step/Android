package com.softsquared.template.kotlin.src.main.map

import android.util.Log
import com.naver.maps.map.overlay.Marker
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.softsquared.template.kotlin.src.main.map.model.AllResponse
import com.softsquared.template.kotlin.src.main.map.model.PopupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapService(val mapFragmentInterface: MapFragment) {
//val accessToken="Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImZvb3RzdGVwQG5hdmVyLmNvbSIsImlhdCI6MTY3NDkxNDc2NiwiZXhwIjoxNjc1MjE3MTY2fQ.KxwX1Q0o-omU1rRIiUJBd9gLPbTRVciP_9g_sklW1Bk"
    fun tryGetMapFootStepList():HashMap<Int,Marker>{
        var return_map=HashMap<Int,Marker>()
//        ApplicationClass.sSharedPreferences.edit().putString(X_ACCESS_TOKEN, accessToken).apply()

        val mapRetrofitInterface=ApplicationClass.sRetrofit.create(MapRetrofitInterface::class.java)
        mapRetrofitInterface.getMapFootStepList().enqueue(object : Callback<AllResponse>{
            override fun onResponse(
                call: Call<AllResponse>,
                response: Response<AllResponse>) {
                Log.d("FootStepList", "tryGetMapFootStepList 되긴하니?")

                if(response.body()!=null)
                    return_map=mapFragmentInterface.onGetMapFootStepListSuccess(response.body() as AllResponse)
                else
                    Log.d("FootStepList", "맵 서비스에서의 결과 : ${response.body().toString()}")
            }

            override fun onFailure(call: Call<AllResponse>, t: Throwable) {
                mapFragmentInterface.onGetMapFootStepListFailure(t.message ?: "통신 오류")
            }

        })
        return return_map
    }

    fun tryGetMapFootStepPopup(place_id:Int){
      //  Log.d("FootStepList", "tryGetMapFootStepPopup 진입")

//        ApplicationClass.sSharedPreferences.edit().putString(X_ACCESS_TOKEN, accessToken).apply()

        val mapRetrofitInterface=ApplicationClass.sRetrofit.create(MapRetrofitInterface::class.java)
        Log.d("FootStepList", "tryGetMapFootStepPopup 안쪽직전진입")

        mapRetrofitInterface.getMapFootStepPopup(place_id).enqueue(object :Callback<PopupResponse>{

            override fun onResponse(
                call: Call<PopupResponse>,
                response: Response<PopupResponse>) {
                Log.d("FootStepList", "tryGetMapFootStepPopup 되긴하니?")

                if(response.body()!=null)
                    mapFragmentInterface.onGetMapFootStepPopupSuccess(response.body() as PopupResponse, place_id)
                else
                    Log.d("FootStepList", "맵 서비스에서의 결과 : ${response.body().toString()}")
            }

            override fun onFailure(call: Call<PopupResponse>, t: Throwable) {
                Log.d("FootStepList", t.message.toString())

                mapFragmentInterface.onGetMapFootStepPopupFailure(t.message ?: "통신 오류")
            }
        })
    }
}