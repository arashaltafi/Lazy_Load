package com.arash.altafi.lazyload.oldWay.kotlin.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {

        var retrofitKotlin: Retrofit? = null

        fun getRetrofit(): Retrofit {
            if (retrofitKotlin == null) {

                retrofitKotlin = Retrofit.Builder()
                    .baseUrl("https://arashaltafi.ir/git/android/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }

            return retrofitKotlin!!
        }

    }

}