package com.arash.altafi.lazyload.newWay.kotlin.di

import com.arash.altafi.lazyload.newWay.kotlin.adapter.PagingAdapter
import com.arash.altafi.lazyload.newWay.kotlin.adapter.StateAdapter
import com.arash.altafi.lazyload.newWay.kotlin.remote.PagingAdapterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Restful2
    @Singleton
    @Provides
    fun providePagingAdapterRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://arashaltafi.ir/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun providePagingAdapterService(@Restful2 retrofit: Retrofit): PagingAdapterService =
        retrofit.create(PagingAdapterService::class.java)

    @Singleton
    @Provides
    fun providePagingAdapter() = PagingAdapter()

    @Singleton
    @Provides
    fun provideStateAdapter() = StateAdapter()

}