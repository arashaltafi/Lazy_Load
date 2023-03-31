package com.arash.altafi.lazyload.java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arash.altafi.lazyload.R;
import com.arash.altafi.lazyload.java.adapter.AdapterCelebrities;
import com.arash.altafi.lazyload.java.api.ApiClient;
import com.arash.altafi.lazyload.java.api.ApiService;
import com.arash.altafi.lazyload.java.model.ResponseCelebritiesJava;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class JavaActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView rcJava;
    private ApiService apiService;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AdapterCelebrities adapterCelebrities;
    private final List<ResponseCelebritiesJava> responseCelebrities = new ArrayList<>();
    private int page = 1;
    private int loadIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        apiService = ApiClient.getRetrofit().create(ApiService.class);
        init();
    }

    private void init() {
        bindViews();
        loadData();
        new Handler().postDelayed(this::getData, 500);
//        new Handler().postDelayed(this::postData,500);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadData() {
        adapterCelebrities = new AdapterCelebrities(JavaActivity.this, rcJava, responseCelebrities);
        rcJava.setAdapter(adapterCelebrities);

        adapterCelebrities.setOnLoadMoreListener(() -> {
            if (responseCelebrities.size() / 5 == page) {
                page++;
                responseCelebrities.add(null);
                loadIndex = responseCelebrities.size() - 1;
                adapterCelebrities.notifyDataSetChanged();

                Handler handler = new Handler();
                handler.postDelayed(this::getData, 2000);
//                    handler.postDelayed(() -> postData(), 2000);
            }
        });
    }

    //with get method
    private void getData() {
        if (page == 1) {
            progressBar.setVisibility(View.VISIBLE);
        }
        apiService.lazyLoad("ارسلان قاسمی", page, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ResponseCelebritiesJava>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(@NonNull List<ResponseCelebritiesJava> responseCelebritiesJavas) {
                        if (page != 1) {
                            responseCelebrities.remove(loadIndex);
                            adapterCelebrities.notifyItemRemoved(loadIndex);
                        }
                        responseCelebrities.addAll(responseCelebritiesJavas);
                        adapterCelebrities.notifyDataSetChanged();
                        adapterCelebrities.setLoading(false);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(JavaActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    //with post method
    private void postData() {
        if (page == 1) {
            progressBar.setVisibility(View.VISIBLE);
        }
        apiService.lazyLoad2("الناز شاکردوست", page, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ResponseCelebritiesJava>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(@NonNull List<ResponseCelebritiesJava> responseCelebritiesJavas) {
                        if (page != 1) {
                            responseCelebrities.remove(loadIndex);
                            adapterCelebrities.notifyItemRemoved(loadIndex);
                        }
                        responseCelebrities.addAll(responseCelebritiesJavas);
                        adapterCelebrities.notifyDataSetChanged();
                        adapterCelebrities.setLoading(false);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(JavaActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void bindViews() {
        rcJava = findViewById(R.id.rc_java);
        progressBar = findViewById(R.id.progress_java);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}