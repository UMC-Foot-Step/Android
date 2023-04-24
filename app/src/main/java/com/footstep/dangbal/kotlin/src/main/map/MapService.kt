package com.footstep.dangbal.kotlin.src.main.map

import android.util.Log
import com.naver.maps.map.overlay.Marker
import com.footstep.dangbal.kotlin.config.ApplicationClass
import com.footstep.dangbal.kotlin.src.main.map.model.AllResponse
import com.footstep.dangbal.kotlin.src.main.map.model.CityResponse
import com.footstep.dangbal.kotlin.src.main.map.model.PopupResponse
import com.footstep.dangbal.kotlin.src.main.map.model.SpecificFstResponse

class MapService(val mapFragmentInterface: MapFragment) {
//val accessToken="Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImZvb3RzdGVwQG5hdmVyLmNvbSIsImlhdCI6MTY3NDkxNDc2NiwiZXhwIjoxNjc1MjE3MTY2fQ.KxwX1Q0o-omU1rRIiUJBd9gLPbTRVciP_9g_sklW1Bk"
    suspend fun tryGetMapFootStepList(){
        var return_map=HashMap<Int,Marker>()
//        ApplicationClass.sSharedPreferences.edit().putString(X_ACCESS_TOKEN, accessToken).apply()

        val mapRetrofitInterface=ApplicationClass.sRetrofit.create(MapRetrofitInterface::class.java)

        try{
            val response=mapRetrofitInterface.getMapFootStepList()
            Log.d("FootStepList", "tryGetMapFootStepList 되긴하니?")

            if(response!=null)
                mapFragmentInterface.onGetMapFootStepListSuccess(response as AllResponse)
            else
                Log.d("FootStepList", "맵 서비스 List에서의 결과 : ${response.toString()}")
        }
        catch (e: Exception) {
            Log.d("FootStepList", "onGetMapFootStepListFailure "+e.message.toString())

            mapFragmentInterface.onGetMapFootStepListFailure(e.message ?: "통신 오류")
        }
    }

    suspend fun tryGetMapFootStepPopup(place_id:Int){
      //  Log.d("FootStepList", "tryGetMapFootStepPopup 진입")

    //    ApplicationClass.sSharedPreferences.edit().putString(X_ACCESS_TOKEN, accessToken).apply()

        val mapRetrofitInterface=ApplicationClass.sRetrofit.create(MapRetrofitInterface::class.java)
        Log.d("FootStepList", "tryGetMapFootStepPopup 안쪽직전진입")

        try{
            val response=mapRetrofitInterface.getMapFootStepPopup(place_id)
            Log.d("FootStepList", "tryGetMapFootStepPopup 되긴하니?")

            if(response!=null) {
                mapFragmentInterface.onGetMapFootStepPopupSuccess(
                    response as PopupResponse,
                    place_id
                )
            }
            else
                Log.d("FootStepList", "맵 서비스 Popup에서의 결과 : ${response.toString()}")
        }
        catch (e: Exception) {
            Log.d("FootStepList", "onGetMapFootStepPopupFailure api"+e.message.toString())

            mapFragmentInterface.onGetMapFootStepPopupFailure(e.message ?: "통신 오류")
        }
    }

    suspend fun tryGetMapFootStepSpecific(start_date :String,ene_date:String){
        val mapRetrofitInterface=ApplicationClass.sRetrofit.create(MapRetrofitInterface::class.java)
        Log.d("FootStepList2", "tryGetMapFootStepSpecific 안쪽직전진입")

        try{
            val response=mapRetrofitInterface.getMapFootStepSpecific(start_date,ene_date)
            Log.d("FootStepList", "tryGetMapFootStepPopup 되긴하니?")

            if(response!=null) {
                mapFragmentInterface.onGetMapFootStepSpecificSuccess(response as SpecificFstResponse)
            }
            else
                Log.d("FootStepList", "맵 서비스 Specific에서의 결과 : ${response.toString()}")
        }
        catch (e: Exception) {
            Log.d("FootStepList", "onGetMapFootStepSpecificFailure"+e.message.toString())

            mapFragmentInterface.onGetMapFootStepSpecificFailure(e.message ?: "통신 오류")
        }
    }

    suspend fun tryGetMapFootStepCity(city :String){
//        ApplicationClass.sSharedPreferences.edit().putString(X_ACCESS_TOKEN, accessToken).apply()

        val mapRetrofitInterface=ApplicationClass.sRetrofit.create(MapRetrofitInterface::class.java)

        try{
            val response=mapRetrofitInterface.getMapFootStepCity(city)
            Log.d("FootStepList", "tryGetMapFootStepCity 되긴하니?")

            if(response!=null)
                mapFragmentInterface.onGetMapFootStepCitySuccess(response as CityResponse)
            else
                Log.d("FootStepList", "맵 서비스 City에서의 결과 : ${response.toString()}")
        }
        catch (e: Exception) {
            Log.d("FootStepList", "tryGetMapFootStepCity onGetMapFootStepListFailure "+e.message.toString())

            mapFragmentInterface.onGetMapFootStepCityFailure(e.message ?: "통신 오류")
        }
    }

}