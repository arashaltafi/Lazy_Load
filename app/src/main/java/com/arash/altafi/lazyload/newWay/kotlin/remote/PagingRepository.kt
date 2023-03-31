package com.arash.altafi.lazyload.newWay.kotlin.remote

import com.arash.altafi.lazyload.newWay.kotlin.base.BaseRepository
import javax.inject.Inject

class PagingRepository @Inject constructor(
    private val service: PagingAdapterService
) : BaseRepository() {

    suspend fun getDataFromAPI(
        page: Int,
        limit: Int
    ) = service.getDataFromAPI(
        page, limit
    )

}