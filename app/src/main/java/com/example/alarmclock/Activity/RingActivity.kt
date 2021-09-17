package com.example.alarmclock.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.alarmclock.Receiver.AlarmReceiver
import com.example.alarmclock.Data.Alarm
import com.example.alarmclock.R
import java.util.*

class RingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ring)

        var btOff = findViewById<Button>(R.id.bt_off)
        var btDelay = findViewById<Button>(R.id.bt_delay)
        var title:String = intent.extras?.getString("Title","").toString()

        btOff.setOnClickListener {
            var intentService: Intent = Intent(applicationContext, AlarmReceiver::class.java)
            intentService.putExtra("handleAlarm","off")
            applicationContext.sendBroadcast(intentService)
            finish()
        }

        btDelay.setOnClickListener {
            var calendar = Calendar.getInstance()
            var intentService: Intent = Intent(applicationContext, AlarmReceiver::class.java)
            intentService.putExtra("handleAlarm","off")
            var newAlarm = Alarm(calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE)+5,"" ,title,false,false,false,false,false,false,false,false)
            newAlarm.scheduleAlarm(this)
            applicationContext.sendBroadcast(intentService)
            finish()
        }


    }
}