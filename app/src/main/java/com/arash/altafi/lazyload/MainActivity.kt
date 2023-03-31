package com.arash.altafi.lazyload

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.lazyload.databinding.ActivityMainBinding
import com.arash.altafi.lazyload.java.JavaActivity
import com.arash.altafi.lazyload.kotlin.KotlinActivity

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() = binding.apply {
        btnJava.setOnClickListener {
            startActivity(Intent(this@MainActivity, JavaActivity::class.java))
        }

        btnKotlin.setOnClickListener {
            startActivity(Intent(this@MainActivity, KotlinActivity::class.java))
        }
    }

}