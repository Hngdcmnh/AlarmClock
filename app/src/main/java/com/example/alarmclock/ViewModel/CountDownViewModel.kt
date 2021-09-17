package com.example.alarmclock.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CountDownViewModel:ViewModel(){
    var timer = Timer()
    lateinit var timerTask: TimerTask
    var time : Long =0
    var timeoutCheck:Boolean = false
    var liveDataTimeoutCheck : MutableLiveData<Boolean> = MutableLiveData()
    var liveDataTime: MutableLiveData<Long> = MutableLiveData()

    init {
        liveDataTimeoutCheck.value = timeoutCheck
        liveDataTime.value = time
    }

    fun startCountDown()
    {
            timeoutCheck = false
            timerTask = object : TimerTask() {
                override fun run() {
                    time -= 1000
                    Log.e("time",time.toString())
                    liveDataTime.postValue(time)
                    if(time<=0) {
                        timeoutCheck = true
                        liveDataTimeoutCheck.postValue(timeoutCheck)
                    }
                }
            }

            timer.scheduleAtFixedRate(timerTask,0,1000)
    }

    fun cancelCountDown()
    {
            time = 0
            liveDataTime.postValue(time)
            timerTask.cancel()
    }

    fun pauseCountDown()
    {
        timerTask.cancel()
    }

}