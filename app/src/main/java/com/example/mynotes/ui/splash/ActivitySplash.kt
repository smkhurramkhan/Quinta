package com.example.mynotes.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.mynotes.databinding.ActivitySplashBinding
import com.example.mynotes.ui.main.MainActivity
import com.example.mynotes.ui.name.ActivityGetName
import com.example.mynotes.utils.SharedPref

class ActivitySplash : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)



        sharedPref = SharedPref(this)



        Handler(Looper.getMainLooper()).postDelayed({
            if (sharedPref.isFirstTime) {
                sharedPref.isFirstTime = false
                startActivity(Intent(this, ActivityGetName::class.java))
                finish()

            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, 2000)

    }
}