package com.example.pakhi

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var countdown: Countdown
    var differenceMB: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        if(auth.currentUser == null)
        {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        setContentView(R.layout.activity_main)
        val storageText: TextView = findViewById(R.id.storageText)
        val storageButton: Button = findViewById(R.id.storagebutton)
        val countdownButton: Button = findViewById(R.id.countdownButton)
        val countdownTest = findViewById<TextView>(R.id.countdownText)
        countdown = Countdown(countdownTest)
        storageButton.setOnClickListener {
            val sharedPref = getSharedPreferences("old storage", MODE_PRIVATE)
            val oldUsed = sharedPref.getFloat("old storage", 0.0f).toDouble()
            val newUsed = getStorage(storageText, storageButton)//Call the function in StorageUtils.kt
            storageText.text = """
                Old Used: ${"%.3f".format(oldUsed)} GB
                New Used: ${"%.3f".format(newUsed)} GB
                """.trimIndent()
            val editor = sharedPref.edit()
            editor.putFloat("old storage", newUsed.toFloat())
            editor.apply()
            differenceMB = ((oldUsed - newUsed) * 1024).toLong()
            }
        countdownButton.setOnClickListener {
            if (differenceMB > 0) {
                countdown.start(differenceMB)
            }
        }
    }
}
