package com.footstep.dangbal.kotlin.src.login.LoginDataSource

import android.util.Log
import com.footstep.dangbal.kotlin.config.ApplicationClass
import com.footstep.dangbal.kotlin.src.login.DataFile.FindPasswordResponse
import com.footstep.dangbal.kotlin.src.login.DataFile.Findpassword
import com.footstep.dangbal.kotlin.src.login.DataFile.LoginResponse
import com.footstep.dangbal.kotlin.src.login.DataFile.User
import com.footstep.dangbal.kotlin.src.login.FindPasswordView
import com.footstep.dangbal.kotlin.src.login.LoginView
import com.footstep.dangbal.kotlin.src.login.TemporApi.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkDataSource {
    companion object TAG{
        val success = "SIGNUP?SUCCESS"
        val fail = "SIGNUP/FAILURE"
    }
    private val api = ApplicationClass.sRetrofit.create(RetrofitInterface::class.java)

    fun login(user: User, loginView: LoginView){
        api.login(user).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if(response.isSuccessful && response.code() == 200){
                    val loginResponse: LoginResponse = response.body()!!
                    Log.d("Tester", "onResponse: ${loginResponse}")
                    when (val code = loginResponse.code){

                        200 -> loginView.onLoginSuccess(code,loginResponse.result!!)
                        else -> loginView.onLoginFailure(loginResponse.message)
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable)
                = loginView.onLoginFailure(t.message?: "통신오류")

        })
    }

    fun autoLogin(jwt:String,loginView: LoginView){
        api.reissue(jwt).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("Tester", "onResponse: dddd${response}")
                if(response.isSuccessful && response.code() == 200){
                    val loginResponse: LoginResponse = response.body()!!
                    when(val code = loginResponse.code){
                        200->loginView.onLoginSuccess(code,loginResponse.result)
                        else -> loginView.onLoginFailure(response.message())
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable){
                loginView.onLoginFailure(t.message?: "통신오류")

            }



        })

    }

    fun findpassword(findpassword: Findpassword, findPasswordview: FindPasswordView){
        val findservice = api.findpassword(findpassword)
        Log.d("Tester", "findpassword: 여기까진 왔음")
        findservice.enqueue(object :Callback<FindPasswordResponse>{
            override fun onResponse(call: Call<FindPasswordResponse>, response: Response<FindPasswordResponse>) {
                Log.d("Tester", "onResponse: 여기도 들림${response.isSuccessful}")
                if(response.isSuccessful && response.code() == 200){
                    val findpasswordresponse: FindPasswordResponse = response.body()!!
                    Log.d("Tester", "onResponse: 거의 다옴 ${findpasswordresponse.result}")
                    when(val code = findpasswordresponse.code){
                        200->findPasswordview.onFindSuccess(code,findpasswordresponse.result)
                        else -> findPasswordview.onFindFailure(response.message())
                    }
                }
            }

            override fun onFailure(call: Call<FindPasswordResponse>, t: Throwable) {
                Log.d("Tester", "onFailure: 여기로 빠짐")
            }
        })


    }


}


