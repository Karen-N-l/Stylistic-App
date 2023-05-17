package com.example.myemobilis_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class signup_designerActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var loginText:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_designer)
        mAuth = FirebaseAuth.getInstance()
        emailEditText = findViewById(R.id.designersignup_email)
        passwordEditText = findViewById(R.id.designerregister_password)
        signUpButton = findViewById(R.id.designerbtn_register)
        loginText=findViewById(R.id.textView_designertwo)
        loginText.setOnClickListener {
            startActivity(Intent(this, DesignerloginActivity::class.java))
        }
        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){
                    task->
                    if (task.isSuccessful){
                        sendVerificationEmail()
                    }else{
                        Toast.makeText(baseContext,"Sign up failed.Please try again.",
                        Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    private fun  sendVerificationEmail(){
        val user = mAuth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                task->
                if (task.isSuccessful){
                    Toast.makeText(baseContext,"Verification email sent.Please check your email.",
                    Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,DesignerloginActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(baseContext,"Failed to send verification email.Please try again.",
                    Toast.LENGTH_SHORT).show()
                }
            }
    }
}