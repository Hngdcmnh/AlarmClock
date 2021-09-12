package com.example.alarmclock.Data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AlarmRepository(private var alarmDao: AlarmDao) {
    val readAllalarm: MutableLiveData<ArrayList<Alarm>> = alarmDao.readAllAlarm()

    suspend fun addAlarm(alarm: Alarm) {
        alarmDao.insertAlarm(alarm)
    }

    suspend fun updateAlarm(alarm: Alarm) {
        alarmDao.updateAlarm(alarm)
    }

    suspend fun deleteAlarm(alarm: Alarm) {
        alarmDao.deleteAlarm(alarm)
    }

    suspend fun deleteAllAlarm() {
        alarmDao.readAllAlarm()
    }




}