package com.cindi.storyou

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.cindi.storyou.ui.login.LoginActivity

class intro : AppCompatActivity() {
    lateinit var startbtn : Button
    lateinit var asguess : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        startbtn = findViewById<Button>(R.id.startbtn)
        startbtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
//        asguess = findViewById<Button>(R.id.asguess)
//        asguess.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
    }
}