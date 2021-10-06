package com.example.alarmclock.Activity

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import com.example.alarmclock.Receiver.AlarmReceiver
import com.example.alarmclock.Data.Alarm
import com.example.alarmclock.Data.AlarmDatabase
import com.example.alarmclock.Data.AlarmRepository
import com.example.alarmclock.R
import com.example.alarmclock.Service.AlarmService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class RingActivity : AppCompatActivity() {
    lateinit var imgAnimation1:ImageView
    lateinit var imgAnimation2:ImageView
    var handlerAnimation:Handler = Handler()
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ring)

        var btOff = findViewById<Button>(R.id.bt_off)
        var btDelay = findViewById<Button>(R.id.bt_delay)
        var title:String = intent.extras?.getString("Title","").toString()
        imgAnimation1 = findViewById(R.id.imgAnimation1)
        imgAnimation2 = findViewById(R.id.imgAnimation2)
//        runnable.run()

        var id = intent.extras?.getInt("id",0)
        lifecycleScope.launch(Dispatchers.IO)
        {
            Log.e(this.javaClass.simpleName,id.toString()+"---")
            if(id!=null)setSwitch(id)
        }

        btOff.setOnClickListener {
            var intentService = Intent(applicationContext, AlarmReceiver::class.java)
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

    private suspend fun setSwitch(id: Int) {
        var alarmDao = AlarmDatabase.getDatabase(applicationContext).alarmDao()
        var alarmRepository = AlarmRepository(alarmDao)

        var alarm = alarmRepository.getAlarm(id)
        if(alarm.repeat==false)
        {
            alarm.on = false
        }
        Log.e(this.javaClass.simpleName,alarm.id.toString())
        alarmRepository.updateAlarm(alarm)

    }

    private var runnable = object : Runnable {
        override fun run() {

            imgAnimation1.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(1000)
                    .withEndAction {
                        imgAnimation1.scaleX = 1f
                        imgAnimation1.scaleY = 1f
                        imgAnimation1.alpha = 1f
                    }

            imgAnimation2.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(700)
                    .withEndAction {
                        imgAnimation2.scaleX = 1f
                        imgAnimation2.scaleY = 1f
                        imgAnimation2.alpha = 1f
                    }

            handlerAnimation.postDelayed(this, 1000)
        }
    }
}