package com.example.studyapp.ui.functions.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutineTimer(
    private val intervalMillis: Long,
    private val onTick: (Long) -> Unit
) {
    private var timeLeft: Long = 0L
    private var job: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main)

    fun start(duration: Long) {
        job?.cancel()
        timeLeft = duration

        job = scope.launch {
            while (timeLeft > 0) {
                onTick(timeLeft)
                delay(intervalMillis)
                timeLeft -= intervalMillis

                if (timeLeft < 0) {
                    timeLeft = 0
                }
            }
            onTick(0)
        }
    }

    fun pause() {
        job?.cancel()
    }

    fun resume() {
        if (timeLeft > 0) {
            job = scope.launch {
                while (timeLeft > 0) {
                    onTick(timeLeft)
                    delay(intervalMillis)
                    timeLeft -= intervalMillis

                    if (timeLeft < 0) {
                        timeLeft = 0
                    }
                }
                onTick(0)
            }
        }
    }

    fun stop() {
        job?.cancel()
        timeLeft = 0
    }
}