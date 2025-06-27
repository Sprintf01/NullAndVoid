package com.example.pakhi

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val storageText: TextView = findViewById(R.id.storageText)
        val storageButton: Button = findViewById(R.id.storagebutton)
        val countdownButton: Button = findViewById(R.id.countdownButton)
        countdownButton.setOnClickListener {
            val intent = Intent(this, CountDownActivity::class.java)
            startActivity(intent)
        }
        getStorage(storageText, storageButton)//Call the function in StorageUtils.kt
    }
}
