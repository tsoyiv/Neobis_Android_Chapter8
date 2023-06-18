package com.example.my_app_eight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.my_app_eight.databinding.ActivityHomeBinding
import com.example.my_app_eight.fragments.login_fragments.LoginFragment
import com.example.my_app_eight.fragments.main_fragments.MainFragment
import com.example.my_app_eight.fragments.main_fragments.profile_fragments.ProfileMenuFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_screen.*
import kotlinx.android.synthetic.main.activity_screen.btn_nav_view

class HomeActivity : AppCompatActivity() {
    //private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        btn_nav_view.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.mainFragment -> {
//                    navController.navigate(R.id.mainFragment)
//                    true
//                }
//                R.id.profileMenuFragment2 -> {
//                    navController.navigate(R.id.profileMenuFragment2)
//                    true
//                }
//                else -> false
//            }
//        }
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