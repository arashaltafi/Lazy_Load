package com.arash.altafi.lazyload.newWay.kotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.arash.altafi.lazyload.newWay.kotlin.base.BaseViewModel
import com.arash.altafi.lazyload.newWay.kotlin.model.UserResponse
import com.arash.altafi.lazyload.newWay.kotlin.remote.PagingAdapterDataSource
import com.arash.altafi.lazyload.newWay.kotlin.remote.PagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PagingAdapterViewModel @Inject constructor(
    private val pagingRepository: PagingRepository
): BaseViewModel() {

    private val _liveGetRepoList = MutableLiveData<PagingData<UserResponse.NewsData.UserData>>()
    val liveGetRepoList: LiveData<PagingData<UserResponse.NewsData.UserData>>
        get() = _liveGetRepoList

    fun getListData() {
        callApiPaging(
            _liveGetRepoList,
            PagingAdapterDataSource(pagingRepository)
        )
    }
}