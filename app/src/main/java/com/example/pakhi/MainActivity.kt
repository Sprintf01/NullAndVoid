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
        setContentView(R.layout.activity_main) // Ensure this layout has a TextView with id: storageText

        val storageButton: Button = findViewById(R.id.button)
        val storageText: TextView = findViewById(R.id.storageText)

        storageText.text = " "

        // 2. StatFs for internal app data (optional alternative)
        storageButton.setOnClickListener {
            val statInt = StatFs(Environment.getDataDirectory().path)
            val totalInt = statInt.blockCountLong * statInt.blockSizeLong
            val freeInt = statInt.availableBlocksLong * statInt.blockSizeLong
            val usedInt = totalInt - freeInt

            // Convert bytes to MB
            fun toMB(bytes: Long): Long = bytes / (1024 * 1024)

            storageText.text = """

            Internal data:
              Used: ${toMB(usedInt)} MB
              Free: ${toMB(freeInt)} MB
              Total: ${toMB(totalInt)} MB
        """.trimIndent()
        }
    }
}
