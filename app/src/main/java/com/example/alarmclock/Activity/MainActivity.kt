package com.example.alarmclock.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.alarmclock.Fragment.CountdownFragment
import com.example.alarmclock.Fragment.ListAlarmFragment
import com.example.alarmclock.Fragment.StopWatchFragment
import com.example.alarmclock.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var navigationView:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView = findViewById<BottomNavigationView>(R.id.nav_bottom)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navigationView.setupWithNavController(navHostFragment.navController)

    }
}

