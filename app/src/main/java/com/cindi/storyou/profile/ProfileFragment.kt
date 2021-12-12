package com.cindi.storyou.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cindi.storyou.R
import com.cindi.storyou.databinding.ProfileFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.cindi.storyou.R.layout.profile_fragment

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }
    //firebase auth
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var viewModel: ProfileViewModel
    private var _binding: ProfileFragment? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = ProfileFragment.inflate(inflater, container, false)
//        val view = binding.root
//        return view
//    }


//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.profile_fragment, container, false)


//    view.signout.setOnClickListener { firebaseAuth.signOut() }}

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}