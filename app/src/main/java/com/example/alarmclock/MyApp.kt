package com.example.alarmclock

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class MyApp :Application(){
    public var CHANNEL_ID: String = "CHANNEL 1"
    public var CHANNEL_ID2: String = "CHANNEL 2"
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = getString(R.string.channel_name)
            val name2 = "Channel_2"
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_LOW

            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            val mChannel2 = NotificationChannel(CHANNEL_ID2, name2, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
            notificationManager.createNotificationChannel(mChannel2)
        }
    }

}