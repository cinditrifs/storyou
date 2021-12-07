package com.cindi.storyou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView


private lateinit var navController : NavController
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val nav_view = findViewById<BottomNavigationView>(R.id.nav_view)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)

        nav_view.setupWithNavController(navController)

    }
}