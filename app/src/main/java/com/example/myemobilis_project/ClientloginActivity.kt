package com.example.myemobilis_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


class ClientloginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var forgotPassword:TextView
    private lateinit var registerTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientlogin)
        mAuth = FirebaseAuth.getInstance()
        emailEditText= findViewById(R.id.et_email)
        passwordEditText= findViewById(R.id.et_password)
        loginButton = findViewById(R.id.btn_login)
        forgotPassword = findViewById(R.id.tv_forgot_password)
        registerTextView = findViewById(R.id.tv_register)
        forgotPassword.setOnClickListener {
            startActivity(Intent(this, ClientresetActivity::class.java))
        }
        registerTextView.setOnClickListener {
            startActivity(Intent(this, Signup_clientActivity::class.java))
        }
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){
                    task->
                    if (task.isSuccessful){
                        startActivity(Intent(this,ClientsNavbarActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(baseContext,"Authentication failed.Please try again.",
                        Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}