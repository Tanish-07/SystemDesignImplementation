package com.example.systemdesignimplementation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.systemdesignimplementation.databinding.ActivityRegisterAuthenticationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterAuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding: ActivityRegisterAuthenticationBinding by lazy {
            ActivityRegisterAuthenticationBinding.inflate(layoutInflater)
        }

        lateinit var auth:FirebaseAuth

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

        binding.LoginSecondary.setOnClickListener {
            startActivity(Intent(this,LoginAuthenticationActivity::class.java))
            finish()
        }

        binding.RegisterMain.setOnClickListener {
            val email =binding.email.text.toString()
            val username =binding.UserName.text.toString()
            val password = binding.password.text.toString()
            val repassword = binding.repassword.text.toString()

            if(email.isEmpty()||username.isEmpty()||password.isEmpty()||repassword.isEmpty()){
                Toast.makeText(this, "Enter all Details", Toast.LENGTH_SHORT).show()
            }else if (repassword!=password){
                Toast.makeText(this, "Repassword isn't Same", Toast.LENGTH_SHORT).show()
            }else{
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){task->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Registeration Succesfull", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,LoginAuthenticationActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this, "Registeration Failed : ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }
    }
}