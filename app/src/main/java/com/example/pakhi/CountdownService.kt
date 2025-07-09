package com.example.pakhi

import android.app.Notification
import android.os.IBinder
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat.startForeground
import android.app.Service
import android.os.Binder
import androidx.lifecycle.MutableLiveData

class CountdownService: Service()
{
    inner class CountdownBinder: Binder(){
        fun getService(): CountdownService = this@CountdownService
    }

    private lateinit var countdown: Countdown
    val countdownLiveData = MutableLiveData<String>()

    private fun notification(): Notification
    {
        val notif = NotificationCompat.Builder(this, "countdown_channel")
            .setContentTitle("Countdown running...")
            .setOngoing(true)
            .build()
        return notif
    }
    override fun onCreate()
    {
        super.onCreate()
        countdown = Countdown {countdownText ->
            countdownLiveData.postValue(countdownText)

            if(countdownText == "Time's Up!")
            {
                stopForeground(true)
                stopSelf()
            }
        }
        startForeground(1, notification())
    }
    override fun onBind(intent: Intent): IBinder? = CountdownBinder()
}