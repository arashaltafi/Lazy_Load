package com.arash.altafi.lazyload.java.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arash.altafi.lazyload.R;
import com.arash.altafi.lazyload.java.model.ResponseCelebritiesJava;
import com.arash.altafi.lazyload.java.utils.OnLoadMoreListener;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterCelebrities extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ResponseCelebritiesJava> list;
    private OnLoadMoreListener onLoadMoreListener;
    private int sizeIndex;
    private int lastVisible;
    private final int countLoadMore = 2;
    private boolean isLoading = false;
    private final int VIEW_TYPE_RESPONSE = 1;
    private final int VIEW_TYPE_LOADMORE = 2;

    public AdapterCelebrities(Context context, RecyclerView recyclerView, List<ResponseCelebritiesJava> list) {
        this.context = context;
        this.list = list;

        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager != null) {
                    sizeIndex = layoutManager.getItemCount();
                }
                if (layoutManager != null) {
                    lastVisible = layoutManager.findLastVisibleItemPosition();
                }

                if (!isLoading && sizeIndex <= (lastVisible + countLoadMore)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.loadMore();
                    }
                    isLoading = true;
                }

            }
        });
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) != null)
            return VIEW_TYPE_RESPONSE;
        else
            return VIEW_TYPE_LOADMORE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_RESPONSE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bazigar, parent, false);
            return new JavaViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADMORE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false);
            return new JavaLoadMoreViewHolder(view);
        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof JavaViewHolder) {
            ResponseCelebritiesJava celebrity = list.get(position);
            Glide.with(context).load(celebrity.getImage()).into(((JavaViewHolder) holder).imageView);
        } else if (holder instanceof JavaLoadMoreViewHolder) {
            ((JavaLoadMoreViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class JavaViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public JavaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivCelebrity);
        }
    }

    public static class JavaLoadMoreViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public JavaLoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.pr_Loading_More);
        }
    }
}