package com.example.pakhi

import android.os.CountDownTimer

class Countdown(private val callBack: (String) -> Unit){

    private val totalTimeInMs: Long = 1800000
    private val intervalInMs: Long = 1000
    private var timer: CountDownTimer? = null

    fun start(updateInStorage : Long) {

        timer = object : CountDownTimer((updateInStorage * totalTimeInMs)/100, intervalInMs) {
            override fun onTick(millisRemaining: Long) {
                val secondsRemaining = millisRemaining / 1000
                val minutes = secondsRemaining / 60
                val seconds = secondsRemaining % 60
                val hours = minutes/60
                val minutesRemaining = minutes%60

                val countdownText = "$hours: ${minutesRemaining}: $seconds"

                callBack(countdownText)
            }

            override fun onFinish() {
                val countdownText = "Time's Up!"
                callBack(countdownText)
            }
        }.start()
    }
}