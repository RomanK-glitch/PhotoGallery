package com.roman.photogallery

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.roman.photogallery.R
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


const val NOTIFICATION_CHANNEL_ID = "flickr_poll"

class PhotoGalleryApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PhotoGalleryApplication)
            modules(appModule)
        }

        appComponent = DaggerAppComponent.create()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}