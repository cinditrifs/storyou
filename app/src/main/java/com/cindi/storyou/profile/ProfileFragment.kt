package com.cindi.storyou.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cindi.storyou.*
import com.cindi.storyou.R
import com.cindi.storyou.data.KontenModel
import com.cindi.storyou.home.itemAdapter
import com.cindi.storyou.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.profile_fragment.view.*


class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }
    private lateinit var viewModel: ProfileViewModel

    //firebase auth
    private lateinit var firebaseAuth : FirebaseAuth
    //to list View
    lateinit var myProject: ListView   //list view
    lateinit var projectData: MutableList<KontenModel>   //modelnya
    lateinit var adapter: com.cindi.storyou.home.itemAdapter
    private lateinit var ref : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)

        view.signout.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(activity, intro::class.java)
            startActivity(intent)}

        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        val email = firebaseUser!!.email.toString()

        view.email.text = email


        myProject = view.buku
        ref = FirebaseDatabase.getInstance("https://storyou-application-default-rtdb.asia-southeast1.firebasedatabase.app").getReference()
        val query = ref.child("konten").orderByChild("pengarang").equalTo("$email");
        projectData = mutableListOf<KontenModel>()
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot!!.exists() )
                    for (e in dataSnapshot.children ){
                        val post = e.getValue(KontenModel::class.java)
                       projectData.add(post!!)
                    }
                    adapter = itemAdapter(projectData, context)
                    myProject.adapter = adapter
                    myProject.setOnItemClickListener { parent, view, position, id ->
                    val intent = Intent(activity, DetailAutherActivity::class.java)
                    val konten = projectData.get(position)
                    intent.putExtra("data1", konten.sampul);
                    intent.putExtra("data2", konten.judul);
                    intent.putExtra("data3", konten.konten);
                    intent.putExtra("data4", konten.pengarang);
                    startActivity(intent)}

                view.countbook.text = projectData.size.toString()

            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            // Get Post object and use the values to update the UI

        })
        //checkUser()
        return view
    }

//    private fun checkUser() {
//        //if not already login go to intro
//        val firebaseUser = firebaseAuth.currentUser
//        if (firebaseUser == null){
//            //user login
//            val intent = Intent(activity, LoginActivity::class.java)
//            startActivity(intent)
//        }
//
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}