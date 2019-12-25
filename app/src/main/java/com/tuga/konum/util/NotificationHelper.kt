package com.tuga.konum.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.tuga.konum.R

class NotificationHelper(context : Context) : ContextWrapper(context) {

    // Create notification default intent.
    private val pendingIntent = PendingIntent.getActivity(this, 0, Intent(), 0)

    private var channelId = ""

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val chan = NotificationChannel(resources.getString(R.string.track_notification_channelid),
                resources.getString(R.string.track_service_name), NotificationManager.IMPORTANCE_NONE)
            chan.lightColor = Color.BLUE
            chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            service.createNotificationChannel(chan)
            channelId = resources.getString(R.string.track_notification_channelid)
        }
    }

    fun getBuilder(): NotificationCompat.Builder {
        // Create notification builder.
        val builder = NotificationCompat.Builder(this, channelId)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        val largeIconBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_people)
        builder.setLargeIcon(largeIconBitmap)
            .setContentTitle(resources.getString(R.string.app_name))
            .setContentText(resources.getString(R.string.tracking_notification))
            .priority = NotificationCompat.PRIORITY_DEFAULT
        // Make head-up notification.
        builder.setFullScreenIntent(pendingIntent, true)

        return builder
    }
}