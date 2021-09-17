package com.example.alarmclock.Receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.alarmclock.Service.AlarmService
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var handleAlarm : String? = intent?.extras?.getString("handleAlarm")
        var title = intent?.extras?.getString("Title")

//        Log.e("Receiver",handleAlarm.toString())
        var myIntent = Intent(context, AlarmService::class.java)
        myIntent.putExtra("handleAlarm",handleAlarm)
        myIntent.putExtra("Title",title)

        if(intent?.getBooleanExtra("Repeat",false) == false)
        {
            context?.startService(myIntent)
        }
        else
        {
            if(alarmIsToday(intent))
            {
                context?.startService(myIntent)
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