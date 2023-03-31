package com.arash.altafi.lazyload.kotlin.api

import com.arash.altafi.lazyload.kotlin.model.ResponseCelebrities
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {

    @GET("lazy_load/LazyLoad.php")
    fun lazyLoad(
        @Query("name") name: String,
        @Query("page") pageCount: Int,
        @Query("per_page") perPage: Int
    ): Single<List<ResponseCelebrities>>

    @FormUrlEncoded
    @POST("lazy_load/LazyLoad.php")
    fun lazyLoad2(
        @Field("name") name: String,
        @Query("page") pageCount: Int,
        @Query("per_page") perPage: Int
    ): Single<List<ResponseCelebrities>>

}