package com.cindi.storyou.create

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.cindi.storyou.MainActivity
import com.cindi.storyou.R
import com.cindi.storyou.data.KontenModel
import com.cindi.storyou.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.create_fragment.view.*
import kotlinx.android.synthetic.main.item_card.*

class CreateFragment : Fragment() {

    private lateinit var inputSampul : EditText
    private lateinit var inputJudul : EditText
    private lateinit var inputKonten : EditText
    private lateinit var inputPengarang : TextView
    private lateinit var createBtn  : Button

    private lateinit var firebaseAuth : FirebaseAuth

    companion object {
        fun newInstance() = CreateFragment()
    }

    private lateinit var viewModel: CreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, 
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.create_fragment, container, false)
        inputSampul = view.inputSampul
        inputJudul = view.inputJudul
        inputKonten = view.inputKonten
        inputPengarang = view.pengarang
        createBtn = view.createBtn

        createBtn.setOnClickListener {
            CreateData()

            //posting fcm
        }

        return view
    }

    private fun CreateData(){
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        val email = firebaseUser!!.email
        val sampul = inputSampul.text.toString()
        val judul = inputJudul.text.toString()
        val konten = inputKonten.text.toString()
        val pengarang = "$email"

        if (judul.isEmpty() || sampul.isEmpty() || konten.isEmpty()){
            inputSampul.error = "Isi Sampul"
            inputJudul.error = "Isi Judul "
            inputKonten.error = "Isi Konten"
            return
        }
        Toast.makeText(activity, "Cerita berhasil dibuat", Toast.LENGTH_SHORT).show()

        val ref = FirebaseDatabase.getInstance("https://storyou-application-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("konten")
        val kontenId = ref.push().key
        val storyou = KontenModel(kontenId, sampul, judul, pengarang, konten)
        if (kontenId != null) {
            ref.child(kontenId).setValue(storyou)

            view?.let { Navigation.findNavController(it).navigate(R.id.action_createFragment_to_homeFragment) };
            //val intent = Intent(activity, HomeFragment::class.java)
            //startActivity(intent)
            //posting api
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}