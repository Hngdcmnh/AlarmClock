package com.example.alarmclock.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities =[Alarm::class], version = 2, exportSchema = false)
abstract class AlarmDatabase: RoomDatabase(){
    abstract fun alarmDao(): AlarmDao

    companion object{
        private var INSTANCE: AlarmDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }
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
                        "alarm_database",


                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }

        }


    }
}