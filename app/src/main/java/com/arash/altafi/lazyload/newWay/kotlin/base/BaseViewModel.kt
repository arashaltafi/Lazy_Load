package com.arash.altafi.lazyload.newWay.kotlin.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response

open class BaseViewModel : ViewModel() {

    fun <T> callApi(
        networkCall: Flow<Response<T>>,
        liveResult: MutableLiveData<T>? = null,
        onResponse: ((T) -> Unit)? = null
    ) {
        val dispatchRetry: (() -> Unit)?
        dispatchRetry = {
            viewModelScope.launch {
                networkCall.collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.let {
                            liveResult?.value = it
                            onResponse?.invoke(it)
                        }
                    }
                }
            }
        }
        dispatchRetry.invoke()
    }

    fun <T : Any> callApiPaging(
        liveResult: MutableLiveData<PagingData<T>>,
        dataSource: PagingSource<Int, T>,
        onResponse: ((PagingData<T>?) -> Unit)? = null
    ) {
        val dispatchRetry: (() -> Unit)?
        dispatchRetry = {
            viewModelScope.launch {
                Pager(
                    config = PagingConfig(
                        pageSize = 5,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = { dataSource }
                ).flow.cachedIn(viewModelScope).collect {
                    liveResult.value = it
                    onResponse?.invoke(it)
                }

            }
        }
        dispatchRetry.invoke()
    }

}
