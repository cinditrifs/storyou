package com.cindi.storyou.create

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
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
import com.cindi.storyou.service.NotificationApi
import com.cindi.storyou.service.NotificationData
import com.cindi.storyou.service.PushNotification
import com.cindi.storyou.service.Retrofit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.create_fragment.view.*
import kotlinx.android.synthetic.main.item_card.*
import kotlinx.android.synthetic.main.notification.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

// notification
const val TOPIC = "Storyou"

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

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        createBtn.setOnClickListener {
            CreateData()
            //sendNotification()
            //posting fcm
        }
        return view
    }

    val TAG = "CreateFragment"
    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try{
            val response = Retrofit.api.pushNotification(notification)
            if(response.isSuccessful){
                Log.d(TAG, "Response: ${Gson().toJson(response)}")
            } else{
                Log.e(TAG, "ini ga ga sukses=" )
            }
        }
        catch (e:Exception){
            Log.e(TAG, "ini ga keambil")
        }

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

        val ref = FirebaseDatabase.getInstance("https://storyou-application-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("konten")
        val kontenId = ref.push().key
        val storyou = KontenModel(kontenId, sampul, judul, pengarang, konten)
        if (kontenId != null) {
            ref.child(kontenId).setValue(storyou)
            Toast.makeText(activity, "Cerita berhasil dibuat", Toast.LENGTH_SHORT).show()
            view?.let { Navigation.findNavController(it).navigate(R.id.action_createFragment_to_homeFragment) };
            //val intent = Intent(activity, HomeFragment::class.java)
            //startActivity(intent)
            //posting api
        }
        val message = "Ada konten baru nih, buruan baca"
        val tittle = judul
        val nama = pengarang
        if (tittle.isNotEmpty() && message.isNotEmpty()){
            PushNotification(
                NotificationData(tittle, message, nama), "Storyou"
            ).also { sendNotification(it) }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}