package com.example.myemobilis_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class ClientsNavbarActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clients_navbar)
        bottomNavigationView = findViewById(R.id.clients_navbar)
        bottomNavigationView.setOnNavigationItemSelectedListener {
                item->
            when(item.itemId){
                R.id.clienthome ->{
                    supportFragmentManager.beginTransaction()
                        .replace(androidx.fragment.R.id.fragment_container_view_tag,ClientHomeFragment())
                        .commit()
                    true
                }
                R.id.clientsearch -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view_tag,ClientSearchFragment())
                        .commit()
                    true
                }
                R.id.clientsettings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view_tag,ClientSettingsFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view_tag,ClientHomeFragment())
            .commit()
    }

}