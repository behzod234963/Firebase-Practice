package com.example.firebasechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebasechat.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
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
            onChangeListener(reference)

        }

    }



//    getting value from firebase
    fun onChangeListener(reference:DatabaseReference){

        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                binding.apply {

//                    adding data another stroke
                    tvMessage.append("\n")
                    tvMessage.append("Behzod:${ snapshot.value.toString()}")

                }

            }

            override fun onCancelled(error: DatabaseError) {  }

        })

    }

}