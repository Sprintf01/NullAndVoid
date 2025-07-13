//package com.example.pakhi
//
//import android.content.ComponentName
//import android.content.Context
//import android.content.Intent
//import android.content.ServiceConnection
//import android.os.Bundle
//import android.os.IBinder
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import android.widget.Button
//import com.google.firebase.auth.FirebaseAuth
//import android.widget.Toast
//import com.example.pakhi.databinding.ActivityMainBinding
//import com.google.firebase.auth.auth
//import android.content.SharedPreferences
//import androidx.lifecycle.LiveData
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//
//class MainActivity : AppCompatActivity() {
//    private lateinit var auth: FirebaseAuth
//    var differenceMB: Long = 0
//    private var newUsed: Double = 0.0
//    private lateinit var binding: ActivityMainBinding
//    private var countdownService: CountdownService? = null
//
//    private val serviceConnection = object: ServiceConnection
//    {
//        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//            val binder = service as CountdownService.CountdownBinder
//            countdownService = binder.getService().apply
//            {
//                countdownLiveData.observe(this@MainActivity)
//                { timeText ->
//                    binding.countdownText.text = timeText
//                }
//            }
//        }
//        override fun onServiceDisconnected(name: ComponentName?) {
//            countdownService = null
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        auth = Firebase.auth
//        if (auth.currentUser == null) {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//            return
//        }
//
//        bindService(
//            Intent(this, CountdownService::class.java),
//            serviceConnection,
//            Context.BIND_AUTO_CREATE
//        )
//
//        val sharedPref = getSharedPreferences("old storage", MODE_PRIVATE)
//        val oldUsed = sharedPref.getFloat("old storage", 0.0f).toDouble()
//        newUsed = getStorage(binding.storageText)
//        differenceMB = ((oldUsed - newUsed) * 1024).toLong()
//
//        binding.storageText.text = "Storage: ${"%.3f".format(oldUsed)}GB"
//
//        binding.countdownButton.setOnClickListener {
//            if (differenceMB > 0) {
//                countdownService?.startCountdown(differenceMB)
//            } else {
//                updateStorage()
//            }
//        }
//    }
//        private fun updateStorage() {
//            val sharedPref = getSharedPreferences("old storage", MODE_PRIVATE)
//            val editor = sharedPref.edit()
//            editor.putFloat("old storage", newUsed.toFloat())
//            editor.apply()
//            binding.storageText.text = "Storage: ${"%.3f".format(newUsed)} GB"
//
//        }
//    }
//
//
//
///*countdown = Countdown(countdownTest)
//
//binding.countdownButton.setOnClickListener {
//    if (differenceMB > 0) {
//
//        countdownService?.Countdown.start(differenceMB)
//        when(Countdown.onFinish)
//        {
//            "Time's Up!" -> storageText.text = """
//        Storage: ${"%.3f".format(newUsed)} GB
//        """.trimIndent()
//        }
//    }
//}
//}
//}*/

package com.example.pakhi

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.example.pakhi.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private var countdownService: CountdownService? = null
    var differenceMB: Long = 0
    private var newUsed: Double = 0.0
    private var isServiceBound = false


    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as CountdownService.CountdownBinder
            countdownService = binder.getService().apply {
                countdownLiveData.observe(this@MainActivity) { countdownText ->
                    binding.countdownText.text = countdownText
                    if(countdownText == "Time's Up!")
                    {
                        updateStorage()
                    }
                }
                if(isRunning)
                {
                    binding.countdownText.text = "Countdown continuing..."
                }
            }
            isServiceBound = true
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            countdownService = null
            isServiceBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val serviceIntent = Intent(this, CountdownService::class.java)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(serviceIntent)
        }
        else{
            startService(serviceIntent)
        }

        bindService(Intent(this, CountdownService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)

        auth = Firebase.auth
        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val sharedPref = getSharedPreferences("old storage", Context.MODE_PRIVATE)
        val oldUsed = sharedPref.getFloat("old storage", 0.0f).toDouble()
        newUsed = getStorage(binding.storageText)
        differenceMB = ((oldUsed - newUsed) * 1024).toLong()
        binding.storageText.text = "Storage: ${"%.3f".format(oldUsed)} GB"

        binding.countdownButton.setOnClickListener {
            if (differenceMB > 0) {
                Countdown{countdownText ->
                    binding.countdownText.text = countdownText
                    if(countdownText == "Time's Up!")
                    {
                        updateStorage()
                    }
                    countdownService?.countdownLiveData?.postValue(countdownText)
                }.start(differenceMB)
            } else {
                updateStorage()
            }
        }
        binding.username.text = username
    }

    private fun updateStorage() {
        val sharedPref = getSharedPreferences("old storage", Context.MODE_PRIVATE)
        sharedPref.edit().apply {
            putFloat("old storage", newUsed.toFloat())
            apply()
        }
        binding.storageText.text = "Storage: ${"%.3f".format(newUsed)} GB"
    }

    override fun onStop() {
        super.onStop()
        if(isServiceBound)
        {
            unbindService(serviceConnection)
        }
    }
}
