package com.arash.altafi.lazyload

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.lazyload.java.JavaActivity
import com.arash.altafi.lazyload.kotlin.KotlinActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {

        btn_java.setOnClickListener {
            startActivity(Intent(this , JavaActivity::class.java))
        }

        btn_kotlin.setOnClickListener {
            startActivity(Intent(this , KotlinActivity::class.java))
        }

    }

}