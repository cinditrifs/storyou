package com.cindi.storyou.home

import android.content.Context
import android.view.LayoutInflater
import com.cindi.storyou.R
import com.cindi.storyou.data.KontenModel

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

data class itemAdapter(var kontenList:List<KontenModel>, var context: Context? = null) : BaseAdapter(){

    override fun getItem(position: Int): Any {
        return kontenList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return kontenList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //val view: View = View.inflate(context ,R.layout.item_card,null)
        val layoutInflayer : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutInflayer.inflate(R.layout.item_card, null)

        val judul = view.findViewById<TextView>(R.id.judul) as TextView
        val pengarang = view.findViewById<TextView>(R.id.pengarang) as TextView
        val sampul = view.findViewById<ImageView>(R.id.sampul) 
        val intro = view.findViewById<TextView>(R.id.intro) as TextView


        //tv_num.text = (position+1).toString()+"."
        val konten = kontenList.get(position)
        Picasso.get().load(konten.sampul).into(sampul);
        judul.text = konten.judul
        pengarang.text = konten.pengarang
        intro.text = konten.konten
        return view
    }

}