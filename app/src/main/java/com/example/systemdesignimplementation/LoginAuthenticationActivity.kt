package com.example.systemdesignimplementation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.systemdesignimplementation.databinding.ActivityAuthenticationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginAuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
         val binding : ActivityAuthenticationBinding by lazy {
            ActivityAuthenticationBinding.inflate(layoutInflater)
        }

        lateinit var auth: FirebaseAuth

        fun onStart() {
            super.onStart()
            val currentUser : FirebaseUser? = auth.currentUser
            if (currentUser != null){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()



        binding.LoginMain.setOnClickListener{
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if(email.isEmpty()||password.isEmpty()){
                Toast.makeText(this, "Enter full details", Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{task ->
                        if (task.isSuccessful){
                            startActivity(Intent(this,RedditActivity::class.java))
                            finish()
                        }else{
                            Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        binding.RegisterSecondary.setOnClickListener{
            startActivity(Intent(this,RegisterAuthenticationActivity::class.java))
            finish()
        }

        }
    }
