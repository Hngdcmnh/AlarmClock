package com.example.alarmclock.Service

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.alarmclock.Activity.MainActivity
import com.example.alarmclock.R

class CountDownService :Service(){
    lateinit var mediaPlayer:MediaPlayer
    lateinit var countDownTimer:CountDownTimer

    companion object{
        const val START_ACTION="START_COUNT_DOWN"
    }

    override fun onCreate() {
        super.onCreate()
        stopForeground(true)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    @SuppressLint("WrongConstant")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer = MediaPlayer.create(this,R.raw.music1)
        Log.e(this.javaClass.simpleName,"run")
        var time: Long? = intent?.extras?.getLong("time",0)
        time?.let { startCountDown(it*1000) }
        return START_STICKY
    }

    private fun startCountDown(time: Long) {
        Log.e(this.javaClass.simpleName,time.toString())
        countDownTimer = object :CountDownTimer(time,1000)
        {
            override fun onTick(p0: Long) {
                var intent = Intent(START_ACTION)
                intent.putExtra("time",p0/1000)
                Log.e(this.javaClass.simpleName,p0.toString())
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
                notifyCountDown(p0/1000)
            }

            override fun onFinish() {

                stopSelf()
            }
        }.start()
    }

    @SuppressLint("WrongConstant")
    private fun notifyCountDown(p0: Long) {
        var intent = Intent(this,MainActivity::class.java)
        val notifyPendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        val notification: Notification = Notification.Builder(this,"CHANNEL 1")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
                .setAutoCancel(true)
                .setContentTitle("Countdown running")
                .setContentText(formatTime(p0))
                .build()
        var notificationManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(3,notification)
    }

    private fun formatTime(time: Long): String {
        var hour = ((time/3600).toInt())
        var minute = ((time - hour*3600)/60).toInt()
        var second = ((time - hour*3600 - minute*60).toInt())
        return String.format("%02d",hour)+":"+String.format("%02d",minute)+":"+String.format("%02d",second)
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

}
