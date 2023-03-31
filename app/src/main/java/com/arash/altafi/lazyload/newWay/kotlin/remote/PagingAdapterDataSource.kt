package com.arash.altafi.lazyload.newWay.kotlin.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arash.altafi.lazyload.newWay.kotlin.model.UserResponse

class PagingAdapterDataSource(private val pagingRepository: PagingRepository) :
    PagingSource<Int, UserResponse.NewsData.UserData>() {

    override fun getRefreshKey(state: PagingState<Int, UserResponse.NewsData.UserData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserResponse.NewsData.UserData> {
        return try {
            val page = params.key ?: 1
            val response = pagingRepository.getDataFromAPI(page, 10)
            val items = response.body()
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (items?.isEmpty() == true) null else page + 1
            Log.i("test123321", "data source")
            items?.let {
                LoadResult.Page(items, prevKey, nextKey)
            } ?: kotlin.run {
                LoadResult.Page(listOf(), prevKey, nextKey)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}