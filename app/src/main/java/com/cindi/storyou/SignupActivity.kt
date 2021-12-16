package com.cindi.storyou

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cindi.storyou.databinding.ActivitySignupBinding
import com.cindi.storyou.profile.ProfileFragment
import com.cindi.storyou.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySignupBinding

    private lateinit var firebaseAuth : FirebaseAuth
    private var email = ""
    private var username = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        firebaseAuth = FirebaseAuth.getInstance()

        //handle butt0n
        binding.signup.setOnClickListener {
            validateData()
        }

        //signin
        binding.loginhere.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }



    }

    private fun validateData() {
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()
        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.email.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            binding.password.error = "Please enter the password"
        }
        else{
            firebaseSignup()
        }
    }

    private fun firebaseSignup() {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {  
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Account Can create", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProfileFragment::class.java))
                finish()
            }
            .addOnFailureListener{ e->
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
    }

}

