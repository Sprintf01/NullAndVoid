package com.example.pakhi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class CountDownActivity: AppCompatActivity() {
    private lateinit var countdown: Countdown

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown)

        val countdownTest = findViewById<TextView>(R.id.countdownText)
        countdown = Countdown(countdownTest)
        countdown.start()
    }
}

