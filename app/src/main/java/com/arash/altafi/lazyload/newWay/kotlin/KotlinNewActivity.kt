package com.arash.altafi.lazyload.newWay.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.arash.altafi.lazyload.databinding.ActivityKotlinNewBinding
import com.arash.altafi.lazyload.newWay.kotlin.adapter.PagingAdapter
import com.arash.altafi.lazyload.newWay.kotlin.adapter.StateAdapter
import com.arash.altafi.lazyload.newWay.kotlin.base.toGone
import com.arash.altafi.lazyload.newWay.kotlin.viewmodel.PagingAdapterViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class KotlinNewActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityKotlinNewBinding.inflate(layoutInflater)
    }

    private val pagingAdapterViewModel by viewModels<PagingAdapterViewModel>()

    @Inject
    lateinit var stateAdapter: StateAdapter

    @Inject
    lateinit var pagingAdapter: PagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initAdapter()
        initViewModel()
    }

    private fun initAdapter() = binding.apply {
        pagingAdapterViewModel.getListData()
        rvPagingAdapter.apply {
//            val decoration =
//                DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
//            addItemDecoration(decoration)
            stateAdapter.onRetry = { pagingAdapter.retry() }
            adapter = pagingAdapter.withLoadStateFooter(
                stateAdapter
            )
        }
    }

    private fun initViewModel() {
        pagingAdapterViewModel.liveGetRepoList.observe(this) {
            pagingAdapter.submitData(lifecycle, it)
            binding.progress.toGone()
        }
    }
}