package com.cindi.storyou.create

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
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
import com.cindi.storyou.R
import com.cindi.storyou.data.KontenModel
import com.cindi.storyou.service.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.google.gson.Gson
import kotlinx.android.synthetic.main.create_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
        createBtn.setOnClickListener {
            CreateData()
            //sendNotification()
            //posting fcm
            pushNotif()
        }
        return view
    }

    private fun pushNotif() {
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        val email = firebaseUser!!.email
        val judul = inputJudul.text.toString()
        val message = "Ada konten baru nih, buruan baca"
        val nama = "$email"
        val topic = "Storyou"
        var notifReq = DataRequest(message, nama, judul, topic)
        println(notifReq.nama + " ~~~")
        val retro = Retrofit().getInstance("https://storyouapp-1.herokuapp.com").create(NotificationApi::class.java)
        retro.pushNotification(notifReq).enqueue(object : Callback<DataResponse2>{
            override fun onResponse(call: Call<DataResponse2>, response: Response<DataResponse2>) {
                val notif = response.body()
                Log.d(TAG, notif.toString())
            }
            override fun onFailure(call: Call<DataResponse2>, t: Throwable) {
                Log.e(TAG, "ini ga keambil")
            }
        })
    }

    val TAG = "CreateFragment"

    private fun CreateData() {
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        val email = firebaseUser!!.email
        val sampul = inputSampul.text.toString()
        val judul = inputJudul.text.toString()
        val konten = inputKonten.text.toString()
        val pengarang = "$email"
        if (judul.isEmpty() || sampul.isEmpty() || konten.isEmpty()) {
            inputSampul.error = "Isi Sampul"
            inputJudul.error = "Isi Judul "
            inputKonten.error = "Isi Konten"
            return
        }
        val ref =
            FirebaseDatabase.getInstance("https://storyou-application-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("konten")
        val kontenId = ref.push().key
        val storyou = KontenModel(kontenId, sampul, judul, pengarang, konten)
        if (kontenId != null) {
            ref.child(kontenId).setValue(storyou)
            Toast.makeText(activity, "Cerita berhasil dibuat", Toast.LENGTH_SHORT).show()
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_createFragment_to_homeFragment)
            };
//            val title = judul
//            val message = "Ada konten baru nih, buruan baca"
//            val nama = pengarang
//            val topic = "Storyou"
//            var notifReq = DataRequest(message, nama, title, topic)
//            val retro = Retrofit().getInstance("https://storyouapp-1.herokuapp.com").create(NotificationApi::class.java)
//            retro.pushNotification(notifReq).enqueue(object : Callback<DataResponse>{
//                override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
//                    val notif = response.body()
//                    Log.d(TAG, notif.toString())
//
//                }
//                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
//                    Log.e(TAG, "ini ga keambil")
//                }
//            })


//            DataRequest(message, pengarang, judul, "Storyou").also {
//                sendNotification(it) }
//            }
        }

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateViewModel::class.java)
        // TODO: Use the ViewModel
    }



}