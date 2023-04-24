package com.footstep.dangbal.kotlin.config

import com.footstep.dangbal.kotlin.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.footstep.dangbal.kotlin.config.ApplicationClass.Companion.sSharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = sSharedPreferences.getString(X_ACCESS_TOKEN, null)

        // JWT 삽입 확인 (API 연결 테스팅)
//        if (jwtToken != null) {
//            Log.d("jwt 들어왔니?", jwtToken)
//        }
//        else{
//            Log.d("NOPE...", "NOOOOOO")
//        }

        if (jwtToken != null) {
            builder.addHeader("Authorization", jwtToken)
        }
        return chain.proceed(builder.build())
    }
}