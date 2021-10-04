package com.example.alarmclock.ViewModel

import android.app.AlarmManager
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CountUpViewModel:ViewModel(){
    var timer = Timer()
    lateinit var timerTask: TimerTask
    var time : Long = 0
    var timeoutCheck:Boolean = false
    var liveDataTimeoutCheck : MutableLiveData<Boolean> = MutableLiveData()
    var liveDataTime: MutableLiveData<Long> = MutableLiveData()

    init {
        liveDataTimeoutCheck.value = timeoutCheck
        liveDataTime.value = time
    }

    fun startCountUp()
    {
        timeoutCheck = false
        liveDataTimeoutCheck.postValue(timeoutCheck)
        timerTask = object : TimerTask() {
            override fun run() {
                time += 1000
                Log.e("time",time.toString())
                liveDataTime.postValue(time)
                if(time >= AlarmManager.INTERVAL_DAY) {
                    timeoutCheck = true
                    liveDataTimeoutCheck.postValue(timeoutCheck)
                }
            }
        }

        timer.scheduleAtFixedRate(timerTask,0,1000)
    }

    fun cancelCountUp()
    {
        time = 0
        liveDataTime.postValue(time)
        timerTask.cancel()
    }

    fun pauseCountUp()
    {
        timerTask.cancel()
    }

}