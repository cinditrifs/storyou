package com.cindi.storyou

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

private lateinit var Share : ImageView
private lateinit var Like : ImageView


var data1: String? = null
var data2:kotlin.String? = null
var data3:kotlin.String? = null
var data4 = 0




class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //Share
        Share = findViewById<ImageView>(R.id.share)
        Share.setOnClickListener(View.OnClickListener {
            val myIntent = Intent(Intent.ACTION_SEND)
            myIntent.type = "text/plain"
            val shareBody: String = data1.toString() + "\n" + data2 + "\n" + data3
            val shareSub = "Your sub here"
            myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub)
            myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
            startActivity(Intent.createChooser(myIntent, "Share this project using"))
        })

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

        //close
        val close = findViewById<ImageView>(R.id.close).setOnClickListener {
            finish()
        }



    }

    fun favActive(view: View) {
        Toast.makeText(this,"Berhasil menambahkan ke cerita yang anda suka!",Toast.LENGTH_SHORT).show();
    }


}


