package com.example.alarmclock.Receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.alarmclock.Service.AlarmService
import com.example.alarmclock.Service.CountDownService
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var handleAlarm : String? = intent?.extras?.getString("handleAlarm")
        var title = intent?.extras?.getString("Title")
        var hour = intent?.extras?.getInt("hour")
        var minute = intent?.extras?.getInt("minute")

        Log.e(this.javaClass.simpleName,handleAlarm.toString()+" "+hour+" "+minute)
        var myIntent = Intent(context, AlarmService::class.java)
        myIntent.putExtra("handleAlarm",handleAlarm)
        myIntent.putExtra("Title",title)

        if(handleAlarm=="off") // turn off alarm
        {
            context?.stopService(myIntent)
        }
        else // turn on alarm
        {
            if(intent?.getBooleanExtra("Repeat",false) == false)
            {
                context?.startForegroundService(myIntent)
            }
            else
            {
                if(alarmIsToday(intent))
                {
                    context?.startForegroundService(myIntent)
                }
            }
        }
    }

    private fun alarmIsToday(intent: Intent?): Boolean {
        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        var today = calendar.get(Calendar.DAY_OF_WEEK)
        when(today)
        {
            Calendar.MONDAY -> { if(intent?.getBooleanExtra("Mon",false) == true) return true}
            Calendar.TUESDAY-> { if(intent?.getBooleanExtra("Tue",false) == true) return true}
            Calendar.WEDNESDAY -> { if(intent?.getBooleanExtra("Wed",false) == true) return true}
            Calendar.THURSDAY -> { if(intent?.getBooleanExtra("Thu",false) == true) return true}
            Calendar.FRIDAY -> { if(intent?.getBooleanExtra("Fri",false) == true) return true}
            Calendar.SATURDAY -> { if(intent?.getBooleanExtra("Sat",false) == true) return true}
            Calendar.SUNDAY -> { if(intent?.getBooleanExtra("Sun",false) == true) return true}
        }
        return false
    }
}