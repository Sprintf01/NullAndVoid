package com.example.pakhi

import android.os.Environment
import android.os.StatFs
import android.widget.Button
import android.widget.TextView

fun getStorage(storageText: TextView, storageButton: Button){ //Function to display storage information of the android device
    storageText.text = " "

    storageButton.setOnClickListener {
        val statInt = StatFs(Environment.getDataDirectory().path)
        val totalInt = statInt.blockCountLong * statInt.blockSizeLong
        val freeInt = statInt.availableBlocksLong * statInt.blockSizeLong
        val usedInt = totalInt - freeInt

        // Convert bytes to GB
        fun toGB(bytes: Long): Long = bytes/(1024*1024*1024)
        storageText.text = """
                Internal data:
                Used: ${toGB(usedInt)} GB
                Free: ${toGB(freeInt)} GB
                Total: ${toGB(totalInt)} GB
            """.trimIndent()
    }
}