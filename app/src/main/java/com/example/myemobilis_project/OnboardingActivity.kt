package com.example.myemobilis_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class OnboardingActivity : AppCompatActivity() {
    private lateinit var getStartedButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pref = applicationContext.getSharedPreferences("MyPref", MODE_PRIVATE)
        val isFirstTime = pref.getBoolean("isFirstTime",true)
        if (isFirstTime){
            with(pref.edit()){
                putBoolean("isFirstTime",false)
                apply()
            }
        }else{
            val intent = Intent(this@OnboardingActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        setContentView(R.layout.activity_onboarding)
        getStartedButton = findViewById(R.id.getStartedButton)
        getStartedButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}