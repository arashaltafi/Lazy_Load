package com.arash.altafi.lazyload.kotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arash.altafi.lazyload.R
import com.arash.altafi.lazyload.kotlin.model.ResponseCelebrities
import com.arash.altafi.lazyload.kotlin.utils.OnLoadMoreListenerKotlin
import com.bumptech.glide.Glide

class AdapterCelebrities(list2: ArrayList<ResponseCelebrities?>, recyclerView: RecyclerView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: ArrayList<ResponseCelebrities?> = ArrayList()
    private var onLoadMoreListener: OnLoadMoreListenerKotlin? = null
    private var sizeIndex = 0
    private var lastVisible = 0
    private val countLoadMore = 2
    private var isLoading = false

    init {

        this.list = list2

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                sizeIndex = layoutManager!!.itemCount
                lastVisible = layoutManager.findLastVisibleItemPosition()
                if (!isLoading && sizeIndex <= lastVisible + countLoadMore) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener!!.loadMore()
                    }
                    isLoading = true
                }
            }
        })
    }

    fun setLoading(loading: Boolean) {
        isLoading = loading
    }

    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListenerKotlin?) {
        this.onLoadMoreListener = onLoadMoreListener
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] != null) VIEW_TYPE_RESPONSE else VIEW_TYPE_LOADMORE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_RESPONSE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_bazigar, parent, false)
            return KotlinViewHolder(view)
        }
        else if (viewType == VIEW_TYPE_LOADMORE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_load_more, parent, false)
            return KotlinLoadMoreViewHolder(view)
        }
        return null!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is KotlinViewHolder) {
            val celebrity = list[position]
            Glide.with(holder.itemView.context).load(celebrity!!.image).into(holder.imageView)
        }
        else if (holder is KotlinLoadMoreViewHolder) {
            (holder).progressBar.isIndeterminate = true
        }
    }

    override fun getItemCount(): Int = list.size

    companion object {
        private const val VIEW_TYPE_RESPONSE = 1
        private const val VIEW_TYPE_LOADMORE = 2
    }

}

class KotlinViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    val imageView : ImageView = itemView.findViewById(R.id.image_celebrity)

}

class KotlinLoadMoreViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    val progressBar : ProgressBar = itemView.findViewById(R.id.pr_Loading_More)

}