package com.softsquared.template.kotlin.src.main.gallery.models

import com.google.gson.annotations.SerializedName

data class ResultPostListByDate(
    @SerializedName("postingListDto") val post_list: ArrayList<PostList>
)
