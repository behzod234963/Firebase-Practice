package com.example.firebasechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.firebasechat.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.play.integrity.internal.t
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initView()

    }

    private fun initView() {

        binding.apply {


            firebaseAuth = Firebase.auth
            launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

                val task=GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {

                    val account=task.getResult(ApiException::class.java)
                    if (account!=null){

                        firebaseAuth(account.idToken!!)

                    }

                }catch (_:ApiException){

                    Toast.makeText(this@SignIn, "fail", Toast.LENGTH_SHORT).show()

                }

            }

            btnSignIn.setOnClickListener {

                signInWithGoogle()

            }

        }


    }

    private fun getClient(): GoogleSignInClient {

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("10")
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(this, gso)

    }

    private fun signInWithGoogle() {

        val signInClient = getClient()
        launcher.launch(signInClient.signInIntent)

    }

    private fun firebaseAuth(idToken: String) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {

            if (it.isSuccessful) {

                Toast.makeText(this, "sucessfull", Toast.LENGTH_SHORT).show()

            } else {

                Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show()

            }

        }

    }

}