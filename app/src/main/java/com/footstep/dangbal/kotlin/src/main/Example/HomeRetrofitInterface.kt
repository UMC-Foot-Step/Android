package com.footstep.dangbal.kotlin.src.main.Example

import com.footstep.dangbal.kotlin.src.main.Example.models.PostSignUpRequest
import com.footstep.dangbal.kotlin.src.main.Example.models.SignUpResponse
import com.footstep.dangbal.kotlin.src.main.Example.models.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface HomeRetrofitInterface {
    @GET("/template/users")
    fun getUsers() : Call<UserResponse>

    @POST("/template/users")
    fun postSignUp(@Body params: PostSignUpRequest): Call<SignUpResponse>
}
