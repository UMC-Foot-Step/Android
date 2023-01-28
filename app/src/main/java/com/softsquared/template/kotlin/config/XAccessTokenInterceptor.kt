package com.softsquared.template.kotlin.config

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.sSharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = sSharedPreferences.getString(X_ACCESS_TOKEN, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImZvb3RzdGVwQG5hdmVyLmNvbSIsImlhdCI6MTY3NDY2MDA5MSwiZXhwIjoxNjc0OTYyNDkxfQ.W7MNMFI43SPbcw5pLhpbsuic0_nCDRcqHKPgEipV9ko")


        if (jwtToken != null) {
            Log.d("jwt 들어왔니?", jwtToken)
        }
        else{
            Log.d("NOPE...", "NOOOOOO")
        }

        if (jwtToken != null) {
            builder.addHeader("Authorization", jwtToken)
        }
        return chain.proceed(builder.build())
    }
}