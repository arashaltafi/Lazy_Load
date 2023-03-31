package com.arash.altafi.lazyload.newWay.kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arash.altafi.lazyload.databinding.ItemStateAdapterBinding

class StateAdapter : LoadStateAdapter<StateAdapter.ViewHolder>() {

    lateinit var onRetry: () -> Unit
    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) = holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = ViewHolder(
        ItemStateAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    inner class ViewHolder(
        private val binding: ItemStateAdapterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetryLoadState.setOnClickListener { onRetry() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            prgLoadState.isVisible = loadState is LoadState.Loading
            btnRetryLoadState.isVisible = loadState is LoadState.Error
            txtRetryLoadState.isVisible = loadState is LoadState.Error
        }
    }

}