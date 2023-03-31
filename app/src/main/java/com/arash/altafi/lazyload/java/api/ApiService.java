package com.arash.altafi.lazyload.java.api;

import com.arash.altafi.lazyload.java.model.ResponseCelebritiesJava;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("lazy_load/LazyLoad.php")
    Single<List<ResponseCelebritiesJava>> lazyLoad(@Query("name") String name, @Query("page") int pageCount, @Query("per_page") int perPage);

}
