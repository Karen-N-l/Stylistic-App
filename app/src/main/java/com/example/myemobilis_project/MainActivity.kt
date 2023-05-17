package com.example.myemobilis_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var imageView:ImageView
    lateinit var textBelowImage:TextView
    lateinit var clientButton:Button
    lateinit var designerButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.image_view)
        textBelowImage = findViewById(R.id.text_below_image)
        clientButton = findViewById(R.id.client_button)
        designerButton = findViewById(R.id.designer_button)
        clientButton.setOnClickListener {
            startActivity(Intent(this,ClientloginActivity::class.java))
        }
        designerButton.setOnClickListener {
            startActivity(Intent(this,DesignerloginActivity::class.java))
        }

    }
}