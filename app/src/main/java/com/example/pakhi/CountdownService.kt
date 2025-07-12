package com.example.pakhi

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.IBinder
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat.startForeground
import android.app.Service
import android.os.Binder
import android.os.Build
import androidx.lifecycle.MutableLiveData

class CountdownService: Service()
{
    inner class CountdownBinder: Binder(){
        fun getService(): CountdownService = this@CountdownService
    }

    var countdown: Countdown? = null
    val countdownLiveData = MutableLiveData<String>()
    var isRunning = false
    private var remainingMB: Long = 0

    private fun createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel(
                "countdown_channel",
                "Countdown notifs",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows countdown progress"
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }.let { channel->
                getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
            }

        }
    }

    private fun updateNotification()
    {
        val notif = NotificationCompat.Builder(this, "countdown_channel")
            .setContentTitle("Countdown running...")
            .setOngoing(true)
            .build()

        startForeground(1, notif)
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onBind(intent: Intent): IBinder? = CountdownBinder()

    fun startCountdown(differenceMB: Long){
        if(countdown == null){
            remainingMB = differenceMB
            countdown = Countdown {countdownText ->
            countdownLiveData.postValue(countdownText)
                updateNotification()
                isRunning = countdownText != "Time's Up!"
                if (!isRunning){
                    stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
        }
        countdown?.start(remainingMB)
            isRunning = true
    }
}}


