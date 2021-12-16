package com.cindi.storyou.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cindi.storyou.MainActivity
import com.cindi.storyou.R
import com.cindi.storyou.SignupActivity
import com.cindi.storyou.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    lateinit var tosignup : TextView
    //firebase auth
    private lateinit var firebaseAuth : FirebaseAuth
    private var emaill = ""
    private var passwordd = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // handle to sigup activity
        val tosignup = findViewById<TextView>(R.id.tosignup)
        tosignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle

        //handle buttin
        binding.login.setOnClickListener {
            validateData()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


//        val username = binding.email
//        val password = binding.password
//        val login = binding.login
//        val loading = binding.loading
//
//        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
//            .get(LoginViewModel::class.java)
//
//        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
//            val loginState = it ?: return@Observer
//
//            // disable login button unless both username / password is valid
//            login.isEnabled = loginState.isDataValid
//
//            if (loginState.usernameError != null) {
//                username.error = getString(loginState.usernameError)
//            }
//            if (loginState.passwordError != null) {
//                password.error = getString(loginState.passwordError)
//            }
//        })
//
//        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
//            val loginResult = it ?: return@Observer
//
//            loading.visibility = View.GONE
//            if (loginResult.error != null) {
//                showLoginFailed(loginResult.error)
//            }
//            if (loginResult.success != null) {
//                updateUiWithUser(loginResult.success)
//            }
//            setResult(Activity.RESULT_OK)
//
//            //Complete and destroy login activity once successful
//            finish()
//        })
//
//        username.afterTextChanged {
//            loginViewModel.loginDataChanged(
//                username.text.toString(),
//                password.text.toString()
//            )
//        }
//
//        password.apply {
//            afterTextChanged {
//                loginViewModel.loginDataChanged(
//                    username.text.toString(),
//                    password.text.toString()
//                )
//            }
//
//            setOnEditorActionListener { _, actionId, _ ->
//                when (actionId) {
//                    EditorInfo.IME_ACTION_DONE ->
//                        loginViewModel.login(
//                            username.text.toString(),
//                            password.text.toString()
//                        )
//                }
//                false
//            }
//
//            login.setOnClickListener {
//                loading.visibility = View.VISIBLE
//                loginViewModel.login(username.text.toString(), password.text.toString())
//            }
//        }
    }

    private fun validateData() {
        emaill = binding.email.text.toString()
        passwordd = binding.password.text.toString()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(emaill).matches()){
            binding.email.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(passwordd)){
            binding.password.error = "Please enter the password"
        }
        else{
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
       firebaseAuth.signInWithEmailAndPassword(emaill, passwordd)
           .addOnSuccessListener {
               //get user info
               val firebaseUser = firebaseAuth.currentUser
               val email = firebaseUser!!.email
               Toast.makeText(this, "Selamat datang di storyou, $email", Toast.LENGTH_SHORT).show()
           }
           .addOnFailureListener{
               e-> Toast.makeText(this, "Login Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
           }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun checkUser() {
        //if user already login go to home
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            //user login
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

 //   private fun updateUiWithUser(model: LoggedInUserView) {
//        val welcome = getString(R.string.welcome)
//        val displayName = model.displayName
//        // TODO : initiate successful logged in experience
//        Toast.makeText(
//            applicationContext,
//            "$welcome $displayName",
//            Toast.LENGTH_LONG
//        ).show()
//    }
//
//    private fun showLoginFailed(@StringRes errorString: Int) {
//        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
//    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
//fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
//    this.addTextChangedListener(object : TextWatcher {
//        override fun afterTextChanged(editable: Editable?) {
//            afterTextChanged.invoke(editable.toString())
//        }
//
//        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//    })
//}