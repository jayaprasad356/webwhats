package com.vedha.whatsstatussaver

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.vedha.whatsstatussaver.deleted.MediaObserver

class MediaObserverService : Service() {

    private val mediaObserver = MediaObserver()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate() {
        super.onCreate()

        val pendingIntent: PendingIntent = Intent(this, DeletedMessageActivity::class.java)
            .let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
            }

//        val notification: Notification = NotificationCompat.Builder(this,
//            "mediaObserver")
//            .setContentTitle("Media Observer")
//            .setContentText("Watching for new images")
//            .setSmallIcon(R.drawable.ic_delete)
//            .setContentIntent(pendingIntent)
//            .setTicker("Watching for new images")
//            .build()
//
//        startForeground(1337, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaObserver.startWatching()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaObserver.stopWatching()
    }
}
