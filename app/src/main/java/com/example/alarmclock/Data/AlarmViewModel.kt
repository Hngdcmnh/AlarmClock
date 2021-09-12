package com.example.alarmclock.Data

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmViewModel(application: Application): ViewModel() {
//    var listAlarmLiveData: MutableLiveData<ArrayList<Alarm>> = MutableLiveData()
//    var listAlarm: ArrayList<Alarm> = ArrayList()
//
//    init {
//        listAlarmLiveData.value = listAlarm
//    }

//    fun addAlarm(alarm: Alarm) {
//        listAlarm.add(alarm)
//        listAlarmLiveData.value = listAlarm
//    }

    
    var alarmRepository:AlarmRepository
    var readAllAlarm :MutableLiveData<ArrayList<Alarm>> = MutableLiveData()

    init {
        val alarmDao = AlarmDatabase.getDatabase(application).alarmDao()
        alarmRepository = AlarmRepository(alarmDao)
        readAllAlarm = alarmRepository.readAllalarm
    }

    fun addAlarm(alarm: Alarm)
    {
        viewModelScope.launch(Dispatchers.IO) {
            alarmRepository.addAlarm(alarm)
        }
    }

    fun updateAlarm(alarm: Alarm)
    {
        viewModelScope.launch(Dispatchers.IO) {
            alarmRepository.updateAlarm(alarm)
        }
    }

    fun deleteAlarm(alarm: Alarm)
    {
        viewModelScope.launch(Dispatchers.IO) {
            alarmRepository.deleteAlarm(alarm)
        }
    }

    fun deleteAllAlarm()
    {
        viewModelScope.launch(Dispatchers.IO) {
            alarmRepository.deleteAllAlarm()
        }
    }

}