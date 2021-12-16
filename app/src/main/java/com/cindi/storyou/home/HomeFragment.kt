package com.cindi.storyou.home

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.cindi.storyou.DetailActivity
import com.cindi.storyou.R
import com.cindi.storyou.data.KontenModel
import com.cindi.storyou.intro
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.view.*
import kotlinx.android.synthetic.main.home_fragment.view.*
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }
    lateinit var list_konten: ListView   //list view
    lateinit var kontenList: MutableList<KontenModel>   //modelnya
    lateinit var adapter: com.cindi.storyou.home.itemAdapter
    private lateinit var viewModel: HomeViewModel
    private lateinit var ref : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        list_konten = view.buku
        list_konten.isClickable=true
        val url = "https://storyou-application-default-rtdb.asia-southeast1.firebasedatabase.app"
        ref  = FirebaseDatabase.getInstance(url).getReference()
        val query = ref.child("konten")

        kontenList = mutableListOf<KontenModel>()
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot!!.exists()){
                    for (e in dataSnapshot.children){
                        val post = e.getValue(KontenModel::class.java)                    // ...
                        kontenList.add(0, post!!)
                    }
                    adapter = itemAdapter(kontenList, context)
                    list_konten.adapter = adapter
                    list_konten.setOnItemClickListener { parent, view, position, id ->
                        val intent = Intent(activity, DetailActivity::class.java)
                        val konten = kontenList.get(position)
                        intent.putExtra("data1", konten.sampul);
                        intent.putExtra("data2", konten.judul);
                        intent.putExtra("data3", konten.konten);
                        intent.putExtra("data4", konten.pengarang);
                        startActivity(intent)}

                }
           }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}