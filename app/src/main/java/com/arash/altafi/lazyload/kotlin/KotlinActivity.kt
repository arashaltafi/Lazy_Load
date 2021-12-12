package com.arash.altafi.lazyload.kotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.arash.altafi.lazyload.R
import com.arash.altafi.lazyload.kotlin.adapter.AdapterCelebrities
import com.arash.altafi.lazyload.kotlin.api.ApiClient
import com.arash.altafi.lazyload.kotlin.api.ApiService
import com.arash.altafi.lazyload.kotlin.model.ResponseCelebrities
import com.arash.altafi.lazyload.kotlin.utils.OnLoadMoreListenerKotlin
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private val compositeDisposable = CompositeDisposable()
    private var page = 1
    private var loadIndex = 0
    private lateinit var adapterCelebrities: AdapterCelebrities
    private val responseCelebrities = ArrayList<ResponseCelebrities?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

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

    private fun loadData() {
        rc_kotlin.layoutManager = GridLayoutManager(this, 1)
        adapterCelebrities = AdapterCelebrities(responseCelebrities, rc_kotlin)
//        adapterCelebrities = AdapterCelebrities2(responseCelebrities, rc_kotlin)
        rc_kotlin.adapter = adapterCelebrities

        adapterCelebrities.setOnLoadMoreListener(object : OnLoadMoreListenerKotlin {
            @SuppressLint("NotifyDataSetChanged")
            override fun loadMore() {
                if (responseCelebrities.size / 5 == page) {
                    page++
                    responseCelebrities.add(null)
//                    responseCelebrities.clear()
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
        })

    }

    //with get method
    private fun getData() {
        if (page == 1) {
            progress_kotlin.visibility = View.VISIBLE
        }
        apiService.lazyLoad("ترلان پروانه" , page , 5)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<ResponseCelebrities>>{
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
                    progress_kotlin.visibility = View.GONE
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@KotlinActivity, e.message, Toast.LENGTH_LONG).show()
                }

            })
    }

    //with post method
    private fun postData() {
        if (page == 1) {
            progress_kotlin.visibility = View.VISIBLE
        }
        apiService.lazyLoad2("مریلا زارعی" , page , 5)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<ResponseCelebrities>>{
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
                    progress_kotlin.visibility = View.GONE
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@KotlinActivity, e.message, Toast.LENGTH_LONG).show()
                }

            })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (compositeDisposable != null) {
            compositeDisposable.dispose()
            compositeDisposable.clear()
        }
    }

}