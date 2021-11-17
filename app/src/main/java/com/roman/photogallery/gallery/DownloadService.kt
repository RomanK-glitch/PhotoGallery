package com.roman.photogallery.gallery

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.roman.photogallery.R

private const val DOWNLOAD_NOTIFICATION_CHANNEL_ID = "download_notification"
private const val FOREGROUND_SERVICE_ID = 1

class DownloadService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val title = intent?.getStringExtra("title")
        val url = intent?.getStringExtra("url")
        val notification = NotificationCompat
            .Builder(this, DOWNLOAD_NOTIFICATION_CHANNEL_ID)
            .setTicker(resources.getString(R.string.image_downloading))
            .setSmallIcon(android.R.drawable.ic_menu_report_image)
            .setContentTitle(resources.getString(R.string.image_downloading))
            .setContentText(title)
            .setAutoCancel(false)
            .build()
        startForeground(FOREGROUND_SERVICE_ID, notification)

        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.download_notification_channel_name)
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(DOWNLOAD_NOTIFICATION_CHANNEL_ID, name, importance)
            val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notification = NotificationCompat
                .Builder(this, DOWNLOAD_NOTIFICATION_CHANNEL_ID)
                .setTicker(resources.getString(R.string.image_downloaded))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(resources.getString(R.string.image_downloaded))
                .setContentText("Download complete")
                .setAutoCancel(true)
                .build()
            val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.notify(FOREGROUND_SERVICE_ID, notification)
        }
        stopForeground(true)
        stopSelf()
        super.onDestroy()
    }
}