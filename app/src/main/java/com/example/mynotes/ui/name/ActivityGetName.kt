package com.example.mynotes.ui.name

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mynotes.databinding.ActivityGetNameBinding
import com.example.mynotes.ui.main.MainActivity
import com.example.mynotes.utils.SharedPref

class ActivityGetName : AppCompatActivity() {

    private lateinit var binding: ActivityGetNameBinding
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = SharedPref(this)

        binding = ActivityGetNameBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnNext.setOnClickListener {
            val personName = binding.etPersonName.text.toString()

            if (personName.isNotBlank() && personName.isNotEmpty())
                sharedPref.personName = binding.etPersonName.text.toString()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}