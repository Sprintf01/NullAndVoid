package com.example.pakhi

import android.os.Environment
import android.os.StatFs
import android.widget.Button
import android.widget.TextView


fun getStorage(storageText: TextView, storageButton: Button) : Double{ //Function to display storage information of the android device

        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)

        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        val availableBlocks = stat.availableBlocksLong

        val totalSpace = totalBlocks * blockSize
        val freeSpace = availableBlocks * blockSize
        val usedSpace = totalSpace - freeSpace

        fun toGB(bytes: Long): Double = bytes/(1024.0*1024.0*1024.0)
        val used = toGB(usedSpace)
        return used
}
/*fun getStorage(storageText: TextView, storageButton: Button){ //Function to display storage information of the android device
    storageText.text = " "
    val statInt = StatFs(Environment.getDataDirectory().path)
    val totalInt = statInt.blockCountLong * statInt.blockSizeLong
    val freeInt = statInt.availableBlocksLong * statInt.blockSizeLong
    val usedInt = totalInt - freeInt

    // Convert bytes to GB
    fun toGB(bytes: Long): Double = bytes/(1024.0*1024.0*1024.0)
    storageText.text = """
                Internal data:
                Used: ${"%.3f".format(toGB(usedInt))} GB
                Free: ${"%.3f".format(toGB(freeInt))} GB
                Total: ${"%.3f".format(toGB(totalInt))} GB
            """.trimIndent()
    oldUsed = toGB(usedInt)
}*/



/*val statInt = StatFs(Environment.getDataDirectory().path)
val totalInt = statInt.blockCountLong * statInt.blockSizeLong
val freeInt = statInt.availableBlocksLong * statInt.blockSizeLong
val usedInt = totalInt - freeInt*/

// Convert bytes to GB

