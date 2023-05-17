package com.example.myemobilis_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import  android.content.Intent
import android.widget.TextView

class DesignerloginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var forgotTextView:TextView
    private lateinit var registerTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_designerlogin)
        mAuth = FirebaseAuth.getInstance()
        emailEditText = findViewById(R.id.edt_email)
        passwordEditText = findViewById(R.id.edt_password)
        loginButton = findViewById(R.id.button_login)
        forgotTextView = findViewById(R.id.edt_forgot_password)
        registerTextView = findViewById(R.id.layout_reg)
        registerTextView.setOnClickListener{
            startActivity(Intent(this,signup_designerActivity::class.java))
        }
        forgotTextView.setOnClickListener{
            startActivity(Intent(this,DesignerresetActivity::class.java))
        }
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){
                    task->
                    if (task.isSuccessful){
                        startActivity(Intent(this,DesignerNavbarActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(baseContext,"Authentication failed.Please try again.",
                        Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}
