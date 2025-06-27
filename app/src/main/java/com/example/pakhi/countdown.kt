package com.example.pakhi

import android.os.CountDownTimer
import android.widget.TextView

class Countdown(
    private val countdownText: TextView,
    private val totalTimeMs: Long = 1800000,
    private val intervalMs: Long = 1000
) {
    private var timer: CountDownTimer? = null

    fun start() {

        timer = object : CountDownTimer(totalTimeMs, intervalMs) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsUntilFinished = millisUntilFinished / 1000
                val minutes = secondsUntilFinished / 60
                val seconds = secondsUntilFinished % 60

                countdownText.text = "$minutes: $seconds"
            }

            override fun onFinish() {
                countdownText.text = "Time's up!"
            }
        }.start()
    }
}