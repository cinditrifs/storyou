package com.cindi.storyou.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.cindi.storyou.R
import com.cindi.storyou.data.KontenModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
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
        ref = FirebaseDatabase.getInstance("https://storyou-application-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("konten")
        kontenList = mutableListOf<KontenModel>()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot!!.exists()){
                    for (e in dataSnapshot.children){
                        val post = e.getValue(KontenModel::class.java)                    // ...
                        kontenList.add(post!!)
                    }
                    adapter = itemAdapter(kontenList, context)
                    list_konten.adapter = adapter
                }
           }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            // Get Post object and use the values to update the UI

        })

        return view

    }

//    private fun addData() {
//        ref= Firebase.database.reference
//        ref = FirebaseDatabase.getInstance("https://storyou-application-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("konten")
//        ref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (dataSnapshot.exists()){
//                    val post = dataSnapshot.getValue<KontenModel>()
//                    // ...
//                    if (post != null) {
//                        kontenList.add(post)
//                    }
//                }
//                adapter = itemAdapter(kontenList, context)
//
//                list_konten?.adapter = adapter
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//            // Get Post object and use the values to update the UI
//
//            })
//
//
//
//    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}