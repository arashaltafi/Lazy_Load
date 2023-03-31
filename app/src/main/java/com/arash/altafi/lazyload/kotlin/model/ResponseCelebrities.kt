package com.arash.altafi.lazyload.kotlin.model

import com.google.gson.annotations.SerializedName

data class ResponseCelebrities(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("name")
    val name: String? = null
)
