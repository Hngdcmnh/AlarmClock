package com.example.alarmclock.Data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.*
import android.content.Intent
import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.alarmclock.AlarmReceiver
import java.util.*

@Entity(tableName = "alarm_table")
class Alarm {

    @PrimaryKey(autoGenerate = true)
    var id=0
    var hour:Int =0
    var minute: Int =0
    var music:String = ""
    var title:String = ""
    var repeat:Boolean = true
    var Mon:Boolean = false
    var Tue:Boolean = false
    var Wed:Boolean = false
    var Thu:Boolean = false
    var Fri:Boolean = false
    var Sat:Boolean = false
    var Sun:Boolean = false

    constructor(hour:Int,minute:Int,music:String,title:String,repeat:Boolean,Mon : Boolean,Tue : Boolean,Wed : Boolean,Thu : Boolean,Fri : Boolean,Sat : Boolean,Sun : Boolean)
    {
        this.hour = hour
        this.minute = minute
        this.title = title
        this.repeat = repeat
        this.Mon = Mon
        this.Tue = Tue
        this.Wed = Wed
        this.Thu = Thu
        this.Fri = Fri
        this.Sat = Sat
        this.Sun = Sun
    }

    fun scheduleAlarm(context: Context)
    {
        var calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,this.hour)
        calendar.set(Calendar.MINUTE,this.minute)
        Log.e("Key","schedule")
        Log.e("Key1",this.hour.toString())
        Log.e("Key2",this.minute.toString())
        var intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("handleAlarm","on")
        intent.putExtra("Repeat", this.repeat)
        intent.putExtra("Mon",this.Mon)
        intent.putExtra("Tue",this.Tue)
        intent.putExtra("Wed",this.Wed)
        intent.putExtra("Thu",this.Thu)
        intent.putExtra("Fri",this.Fri)
        intent.putExtra("Sat",this.Sat)
        intent.putExtra("Sun",this.Sun)
        intent.putExtra("Title",this.title)
        var pendingIntent = PendingIntent.getBroadcast(context,this.id,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        var alarmManager :AlarmManager = context.applicationContext.getSystemService(ALARM_SERVICE) as AlarmManager
        if(this.repeat == true)
        {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent)
        }
        else
        {
            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pendingIntent)
        }

    }

    fun cancelAlarm(context: Context)
    {
        var intent = Intent(context, AlarmReceiver::class.java)
        var pendingIntent = PendingIntent.getBroadcast(context,this.id,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        var alarmManager :AlarmManager = context.applicationContext.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}

