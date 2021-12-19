package com.cindi.storyou

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.cindi.storyou.create.TOPIC
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging


private lateinit var navController : NavController
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val nav_view = findViewById<BottomNavigationView>(R.id.nav_view)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)

        nav_view.setupWithNavController(navController)
        Firebase.messaging.subscribeToTopic("Storyou")
            .addOnCompleteListener { task ->
                var msg = "Welocome, Start your journey"
                if (!task.isSuccessful) {
                    msg = "not success"
                }
                Log.d(ContentValues.TAG, msg)
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
    }
}