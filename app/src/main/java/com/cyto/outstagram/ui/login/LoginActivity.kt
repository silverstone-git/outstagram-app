package com.cyto.outstagram.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cyto.outstagram.AppStateViewModel
import com.cyto.outstagram.MainActivity
import com.cyto.outstagram.R
import com.cyto.outstagram.databinding.ActivityLoginBinding
import com.cyto.outstagram.util.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val appStateViewModel: AppStateViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)

        val usernameEditText = binding.usernameInput
        val passwordEditText = binding.passwordInput
        val loginButton = binding.loginButton

        loginButton.setOnClickListener {
            val email = usernameEditText.editText.toString()
            val password = passwordEditText.editText.toString()

            // Perform login in a coroutine
            CoroutineScope(Dispatchers.IO).launch {
                val loginResponse = NetworkUtils.login(email, password)

                withContext(Dispatchers.Main) {
                    if (loginResponse != null) {
                        // Save the JWT to SharedPreferences
                        sharedPreferences.edit().putString("jwt", loginResponse.jwt).apply()
                        sharedPreferences.edit().putString("user", loginResponse.user).apply()
                        // Update the app state to logged in
                        appStateViewModel.setLoggedIn(true)

                        // Redirect to MainActivity
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Handle login failure
                        Toast.makeText(
                            this@LoginActivity,
                            "Login failed. Check your credentials.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}