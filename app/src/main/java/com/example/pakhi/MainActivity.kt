package com.example.pakhi

import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val storageText: TextView = findViewById(R.id.storageText)
        val storageButton: Button = findViewById(R.id.button)
        getStorage(storageText, storageButton)//Call the function in StorageUtils.kt
    }
}
