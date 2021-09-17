package com.example.alarmclock.Service

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.alarmclock.R
import com.example.alarmclock.Activity.RingActivity

class AlarmService:Service() {
    lateinit var mediaPlayer: MediaPlayer
    var id:Int =0

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        var handleAlarm:String? = intent?.extras?.getString("handleAlarm")

        if(handleAlarm == "on") {
            id =1
        }
        else if(handleAlarm=="off"){
            id=0
        }

        if(id==1)
        {
            mediaPlayer = MediaPlayer.create(this, R.raw.music1)
            mediaPlayer.start()
            val notifyIntent = Intent(this, RingActivity::class.java)
            val notifyPendingIntent = PendingIntent.getActivity(this,0,notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT)
            val notification: Notification = Notification.Builder(this,"CHANNEL 1")
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
                    .setContentIntent(notifyPendingIntent)
                    .setAutoCancel(true)
                    .setContentTitle("Wonderful music")
                    .setContentText("My Awesome Band")
                    .build()

            var notificationManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1,notification)
            id=0
        }
        else if(id==0)
        {
            mediaPlayer.stop()
            mediaPlayer.reset()
        }
        return START_NOT_STICKY

    }
}