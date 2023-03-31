package com.arash.altafi.lazyload.oldWay.kotlin.api

import com.arash.altafi.lazyload.oldWay.kotlin.model.ResponseCelebrities
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {

    @GET("lazy_load/LazyLoad.php")
    fun lazyLoad(
        @Query("name") name: String,
        @Query("page") pageCount: Int,
        @Query("per_page") perPage: Int
    ): Single<List<ResponseCelebrities>>

}