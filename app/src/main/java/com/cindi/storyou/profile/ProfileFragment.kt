package com.cindi.storyou.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.cindi.storyou.R
import com.cindi.storyou.databinding.ProfileFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.cindi.storyou.R.layout.profile_fragment
import com.cindi.storyou.data.KontenModel
import com.cindi.storyou.home.HomeViewModel
import com.cindi.storyou.home.itemAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.profile_fragment.view.*
import kotlinx.android.synthetic.main.profile_fragment.view.buku


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

        view.signout.setOnClickListener { firebaseAuth.signOut() }

        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        val email = firebaseUser!!.email

        view.email.text = email
//        view.countbook.text = projectData.size.toString()


        myProject = view.buku
        ref = FirebaseDatabase.getInstance("https://storyou-application-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("konten")
        projectData = mutableListOf<KontenModel>()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot!!.exists()  )//&& dataSnapshot.child("konten").hasChild("pengarang") == firebaseUser!!.email.toString(){
                    for (e in dataSnapshot.children ){
                        val post = e.getValue(KontenModel::class.java)                    // ...
                       projectData.add(post!!)
                    }
                    adapter = itemAdapter(projectData, context)
                    myProject.adapter = adapter
                }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            // Get Post object and use the values to update the UI

        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}