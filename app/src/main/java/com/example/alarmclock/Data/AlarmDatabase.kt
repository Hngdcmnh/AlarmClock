package com.example.alarmclock.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities =[Alarm::class], version = 1, exportSchema = false)
abstract class AlarmDatabase: RoomDatabase(){
    abstract fun alarmDao(): AlarmDao

    companion object{
        private var INSTANCE: AlarmDatabase? = null

        fun getDatabase(context: Context):AlarmDatabase
        {
            var tempInstance = INSTANCE
            if(tempInstance != null)
            {
                return tempInstance
            }
            synchronized(this)
            {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AlarmDatabase::class.java,
                        "alarm_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}