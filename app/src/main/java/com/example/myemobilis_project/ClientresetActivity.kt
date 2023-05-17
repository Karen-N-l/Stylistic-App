package com.example.myemobilis_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


class ClientresetActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var resetButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientreset)
        mAuth = FirebaseAuth.getInstance()
        emailEditText = findViewById(R.id.clientemail_reset)
        resetButton = findViewById(R.id.client_resetbtn)
        resetButton.setOnClickListener {
            val email = emailEditText.text.toString()
            mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener{
                    task->
                    if (task.isSuccessful){
                        Toast.makeText(baseContext,"Password reset email sent.Please check your email.",
                        Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(baseContext,"Failed to send password reset email .Please try again.",
                        Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}