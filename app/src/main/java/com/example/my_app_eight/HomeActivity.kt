package com.example.my_app_eight

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.btn_nav_view)
            .setupWithNavController(navController)
    }

    fun hide() {
        val btmNavBar = findViewById<View>(R.id.btm_nav_bar)
        val btn_float = findViewById<FloatingActionButton>(R.id.btn_floating_menu)
        btmNavBar.visibility = View.GONE
        btn_float.visibility = View.GONE
    }

    fun showBtm() {
        val btmNavBar = findViewById<View>(R.id.btm_nav_bar)
        val btn_float = findViewById<FloatingActionButton>(R.id.btn_floating_menu)
        btmNavBar.visibility = View.VISIBLE
        btn_float.visibility = View.VISIBLE
    }

}