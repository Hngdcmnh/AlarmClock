package com.example.alarmclock.Data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
public interface AlarmDao {
    @Insert
    suspend fun insertAlarm(alarm: Alarm)

    @Query("DELETE FROM alarm_table")
    suspend fun deletaAllAlarm()

    @Query("SELECT * FROM alarm_table ORDER BY id ASC")
    fun readAllAlarm(): LiveData<List<Alarm>>

    @Update
    suspend fun updateAlarm(alarm: Alarm)

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

    @Query("SELECT * FROM alarm_table WHERE id=:id ")
    fun getAlarm(id: Int): Alarm

    @Query("SELECT  MAX(id) FROM alarm_table")
    fun getId():Int

}