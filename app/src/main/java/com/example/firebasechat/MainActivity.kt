package com.example.firebasechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebasechat.databinding.ActivityMainBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {

        val database= Firebase.database
        val reference=database.getReference("Messages")
        binding.apply {

            btnSend.setOnClickListener {

                reference.setValue(etMessage.text.toString())

            }

        }

    }
}