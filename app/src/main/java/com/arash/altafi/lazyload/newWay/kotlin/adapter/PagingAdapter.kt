package com.arash.altafi.lazyload.newWay.kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arash.altafi.lazyload.databinding.ItemPagingAdapterBinding
import com.arash.altafi.lazyload.newWay.kotlin.model.UserResponse
import com.bumptech.glide.Glide

class PagingAdapter :
    PagingDataAdapter<UserResponse.NewsData.UserData, PagingAdapter.PagingViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<UserResponse.NewsData.UserData>() {
        override fun areItemsTheSame(
            oldItem: UserResponse.NewsData.UserData,
            newItem: UserResponse.NewsData.UserData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserResponse.NewsData.UserData,
            newItem: UserResponse.NewsData.UserData
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        val binding = ItemPagingAdapterBinding.inflate(LayoutInflater.from(parent.context))
        return PagingViewHolder(binding)
    }

    inner class PagingViewHolder(private val binding: ItemPagingAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userData: UserResponse.NewsData.UserData) = binding.apply {
            Glide.with(root.context).load(userData.avatar).into(imageView)
            tvDesc.text = userData.family
            tvName.text = userData.name
        }
    }

}