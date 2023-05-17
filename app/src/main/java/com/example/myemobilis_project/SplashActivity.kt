package com.example.myemobilis_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

object Holder{
    const val SPLASH_DELAY = 3000L
}

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            val intent = Intent(this@SplashActivity,OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        },Holder.SPLASH_DELAY)
        setContentView(R.layout.activity_splash2)
    }
}