package com.arash.altafi.lazyload.oldWay.java.api;

import com.arash.altafi.lazyload.oldWay.java.model.ResponseCelebritiesJava;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("lazy_load/LazyLoad.php")
    Single<List<ResponseCelebritiesJava>> lazyLoad(@Query("name") String name, @Query("page") int pageCount, @Query("per_page") int perPage);

}
