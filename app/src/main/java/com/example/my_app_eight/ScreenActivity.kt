package com.example.my_app_eight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ScreenActivity : AppCompatActivity() {

    private lateinit var binding: ScreenActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen)

    }
//    fun hide() {
//        binding.btm_nav_bar.visibility = View.GONE
//    }
//    fun showBtm() {
//        binding.btm_nav_bar.visibility = View.VISIBLE
//    }
}