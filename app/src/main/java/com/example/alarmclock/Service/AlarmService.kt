package com.example.alarmclock.Service

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.startForegroundService
import com.example.alarmclock.R
import com.example.alarmclock.Activity.RingActivity

class AlarmService:Service() {
    lateinit var mediaPlayer: MediaPlayer
    var idMedia:Int =0

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer = MediaPlayer.create(this, R.raw.music1)

        //Get intent include handleAlarm (turn on/off media) & id of Alarm
        var handleAlarm:String? = intent?.extras?.getString("handleAlarm")
        var id: Int? = intent?.extras?.getInt("id",0)

        // Handle media
        if(handleAlarm == "on") {
            idMedia =1
        }
        else if(handleAlarm=="off"){
            idMedia=0
        }
        if(idMedia==1)
        {
            // Start RingActivity
            val notifyIntent = Intent(this, RingActivity::class.java)
            notifyIntent.putExtra("id",id)


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
            Log.e(this.javaClass.simpleName,"media")
            mediaPlayer.start()
            startForeground(1,notification)
            idMedia=0
        }
        else if(idMedia==0)
        {
            mediaPlayer.stop()
            mediaPlayer.reset()
        }
        return START_NOT_STICKY

    }

    override fun onDestroy() {
        Log.e(this.javaClass.simpleName,"destroy")
        super.onDestroy()
        mediaPlayer.stop()
    }
}