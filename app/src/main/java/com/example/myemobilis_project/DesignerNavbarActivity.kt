package com.example.myemobilis_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class DesignerNavbarActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_designer_navbar)
        bottomNavigationView = findViewById(R.id.designers_navbar)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            item->
            when(item.itemId){
                R.id.navigation_home ->{
                    supportFragmentManager.beginTransaction()
                        .replace(androidx.fragment.R.id.fragment_container_view_tag,DesignerHomeFragment())
                        .commit()
               true
                }
                R.id.navigation_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view_tag,DesignerSearchFragment())
                        .commit()
                    true
                }
                R.id.navigation_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view_tag,DesignerSettingsFragment())
                        .commit()
                    true
                }
                R.id.navigation_profile ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view_tag,ViewFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view_tag,DesignerHomeFragment())
            .commit()
    }
}