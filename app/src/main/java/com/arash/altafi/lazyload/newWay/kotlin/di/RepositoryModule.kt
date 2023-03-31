package com.arash.altafi.lazyload.newWay.kotlin.di

import com.arash.altafi.lazyload.newWay.kotlin.remote.PagingAdapterService
import com.arash.altafi.lazyload.newWay.kotlin.remote.PagingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun providePagingRepository(
        service: PagingAdapterService,
    ) = PagingRepository(service)

}