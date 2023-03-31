package com.arash.altafi.lazyload.kotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.arash.altafi.lazyload.databinding.ActivityKotlinBinding
import com.arash.altafi.lazyload.kotlin.adapter.AdapterCelebrities
import com.arash.altafi.lazyload.kotlin.api.ApiClient
import com.arash.altafi.lazyload.kotlin.api.ApiService
import com.arash.altafi.lazyload.kotlin.model.ResponseCelebrities
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class KotlinActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityKotlinBinding.inflate(layoutInflater)
    }

    private lateinit var apiService: ApiService
    private val compositeDisposable = CompositeDisposable()
    private var page = 1
    private var loadIndex = 0
    private lateinit var adapterCelebrities: AdapterCelebrities
    private val responseCelebrities = ArrayList<ResponseCelebrities?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        apiService = ApiClient.getRetrofit().create(ApiService::class.java)
        init()
    }

    private fun init() {
        loadData()

        Handler(Looper.myLooper()!!).postDelayed({
            getData()
        }, 500)

//        Handler(Looper.myLooper()!!).postDelayed({
//            postData()
//        }, 500)

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadData() = binding.apply {
        adapterCelebrities = AdapterCelebrities(responseCelebrities, rcKotlin)
        rcKotlin.adapter = adapterCelebrities

        adapterCelebrities.onLoadMoreListener = {
            if (responseCelebrities.size / 5 == page) {
                page++
                responseCelebrities.add(null)
                loadIndex = responseCelebrities.size - 1
                adapterCelebrities.notifyDataSetChanged()
                Handler(Looper.myLooper()!!).postDelayed({
                    getData()
                }, 2000)
//                    Handler(Looper.myLooper()!!).postDelayed({
//                        postData()
//                    }, 2000)

            }
        }
    }

    //with get method
    private fun getData() = binding.apply {
        if (page == 1) {
            progressKotlin.visibility = View.VISIBLE
        }
        apiService.lazyLoad("ترلان پروانه", page, 5)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<ResponseCelebrities>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onSuccess(t: List<ResponseCelebrities>) {
                    if (page != 1) {
                        responseCelebrities.removeAt(loadIndex)
                        adapterCelebrities.notifyItemRemoved(loadIndex)
                    }
                    responseCelebrities.addAll(t)
                    adapterCelebrities.notifyDataSetChanged()
                    adapterCelebrities.setLoading(false)
                    progressKotlin.visibility = View.GONE
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@KotlinActivity, e.message, Toast.LENGTH_LONG).show()
                }

            })
    }

    //with post method
    private fun postData() = binding.apply {
        if (page == 1) {
            progressKotlin.visibility = View.VISIBLE
        }
        apiService.lazyLoad2("مریلا زارعی", page, 5)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<ResponseCelebrities>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onSuccess(t: List<ResponseCelebrities>) {
                    if (page != 1) {
                        responseCelebrities.removeAt(loadIndex)
                        adapterCelebrities.notifyItemRemoved(loadIndex)
                    }
                    responseCelebrities.addAll(responseCelebrities)
                    adapterCelebrities.notifyDataSetChanged()
                    adapterCelebrities.setLoading(false)
                    progressKotlin.visibility = View.GONE
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@KotlinActivity, e.message, Toast.LENGTH_LONG).show()
                }

            })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }

}