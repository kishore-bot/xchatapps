package com.example.x_chat.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.x_chat.R

class InAppMessageNotification (
    private val context: Context,
    private val title: String,
    private val message: String,
) {
    private val channelId: String = "FCM100"
    private val channelName: String = "Messages"
    private val notificationManager =
        context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var notificationBuilder: NotificationCompat.Builder

    @RequiresApi(Build.VERSION_CODES.O)
    fun fireNotification() {
        notificationChannel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)
        notificationBuilder = NotificationCompat.Builder(context, channelId)
        notificationBuilder.setSmallIcon(R.drawable.speech_bubble)
        notificationBuilder.setContentTitle(title)
        notificationBuilder.setContentText(message)
        notificationBuilder.setAutoCancel(true)
        notificationManager.notify(100, notificationBuilder.build())
    }
}