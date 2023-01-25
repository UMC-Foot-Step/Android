package com.softsquared.template.kotlin.src.main.gallery

import com.softsquared.template.kotlin.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.softsquared.template.kotlin.src.main.Example.models.PostSignUpRequest
import com.softsquared.template.kotlin.src.main.Example.models.SignUpResponse
import com.softsquared.template.kotlin.src.main.Example.models.UserResponse
import com.softsquared.template.kotlin.src.main.gallery.models.PostListResponse
import retrofit2.Call
import retrofit2.http.*

interface GalleryRetrofitInterface {

    @GET("/footstep/gallery")
    fun getGalleryPostList() : Call<PostListResponse>

}
