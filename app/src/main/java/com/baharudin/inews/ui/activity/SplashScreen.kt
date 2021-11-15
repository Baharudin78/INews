package com.baharudin.inews.ui.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.baharudin.inews.R
import com.baharudin.inews.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    binding.ivSplash.setImageResource(R.drawable.inews_black)
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    binding.ivSplash.setImageResource(R.drawable.inews)
                }
            }



        Handler(Looper.getMainLooper()).postDelayed({
            if (user != null) {
                val intentHome = Intent(this, MainActivity::class.java)
                startActivity(intentHome)
            }else {
                val intentLogin = Intent(this, LoginActivity::class.java)
                startActivity(intentLogin)
            }
        }, 1000)
    }
}