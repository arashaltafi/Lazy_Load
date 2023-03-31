package com.arash.altafi.lazyload

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.lazyload.databinding.ActivityMainBinding
import com.arash.altafi.lazyload.newWay.kotlin.KotlinNewActivity
import com.arash.altafi.lazyload.oldWay.java.JavaOldActivity
import com.arash.altafi.lazyload.oldWay.kotlin.KotlinOldActivity

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
        //Old Way
        btnOldWayJava.setOnClickListener {
            startActivity(Intent(this@MainActivity, JavaOldActivity::class.java))
        }

        btnOldWayKotlin.setOnClickListener {
            startActivity(Intent(this@MainActivity, KotlinOldActivity::class.java))
        }

        //New Way
        btnNewWayKotlin.setOnClickListener {
            startActivity(Intent(this@MainActivity, KotlinNewActivity::class.java))
        }
    }

}