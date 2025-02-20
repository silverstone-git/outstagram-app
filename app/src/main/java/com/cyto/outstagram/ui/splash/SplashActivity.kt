package com.cyto.outstagram.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.cyto.outstagram.AppStateViewModel
import com.cyto.outstagram.MainActivity
import com.cyto.outstagram.R
import com.cyto.outstagram.util.JwtUtils
import com.cyto.outstagram.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val appStateViewModel: AppStateViewModel by viewModels()
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)

        // Observe the isLoggedIn state
        appStateViewModel.isLoggedIn.observe(this, Observer { isLoggedIn ->
            if (isLoggedIn) {
                Log.d("SplashActivity", "User is logged in, redirecting to MainActivity")
                redirectToMainActivity()
            } else {
                Log.d("SplashActivity", "User is not logged in, redirecting to LoginActivity")
                redirectToLoginActivity()
            }
        })

        // Simulate a delay for the splash screen
        handler.postDelayed({
            checkJwtAndRedirect()
        }, 2000) // 2 seconds delay
    }

    private fun checkJwtAndRedirect() {
        val jwt = sharedPreferences.getString("jwt", null)
        val isValid = JwtUtils.isValidJwt(jwt)

        if (isValid) {
            // Update the app state to logged in
            appStateViewModel.setLoggedIn(true)
        } else {
            // Clear the invalid JWT from cache
            sharedPreferences.edit().remove("jwt").apply()
            // Update the app state to logged out
            appStateViewModel.setLoggedIn(false)
        }
    }

    private fun redirectToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun redirectToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}