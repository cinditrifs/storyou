package com.cindi.storyou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso

class DetailAutherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_auther)

        //getExtra judul
        val getjudul= getIntent().getExtras()?.getString("data2");
        val judul = findViewById<TextView>(R.id.judul) as TextView
        judul.text = getjudul.toString()
        //getExtra konten
        val getkonten= getIntent().getExtras()?.getString("data3");
        val konten = findViewById<TextView>(R.id.konten) as TextView
        konten.text = getkonten.toString()
        //getExtra pengarang
        val getpengarang= getIntent().getExtras()?.getString("data4");
        val pengarang = findViewById<TextView>(R.id.pengarang) as TextView
        pengarang.text = getpengarang.toString()
        //getExtra sampul
        val getsampul= getIntent().getExtras()?.getString("data1");
        val sampul = findViewById<ImageView>(R.id.sampul)
        Picasso.get().load(getsampul).into(sampul);

        //toast delete and edit
        val soon = findViewById<LinearLayout>(R.id.fitursoon)
        soon.setOnClickListener {
            Toast.makeText(this, "Opps, fitur ini sedang dalam perbaikan", Toast.LENGTH_SHORT).show()
        }

        //close
        val close = findViewById<ImageView>(R.id.close).setOnClickListener {
            finish()
        }

    }

}