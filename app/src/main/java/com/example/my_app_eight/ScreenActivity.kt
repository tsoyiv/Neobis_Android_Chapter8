package com.example.my_app_eight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.my_app_eight.fragments.main_fragments.MainFragment
import kotlinx.android.synthetic.main.activity_screen.*

class ScreenActivity : AppCompatActivity() {

    private lateinit var binding: ScreenActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen)

        btn_nav_view.background = null
        btn_nav_view.menu.getItem(2).isEnabled = false

//        val navHostFragment = supportFragmentManager.findFragmentById(binding.hostFragment.id) as MainFragment
//        val navController = findNavController(R.id.hostFragment)
//        binding.btn_nav_view.setupWithNavController(navController)

    }
}