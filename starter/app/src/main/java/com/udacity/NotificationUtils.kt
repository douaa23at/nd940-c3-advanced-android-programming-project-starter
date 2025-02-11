package com.udacity

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.udacity.MainActivity.Companion.CHANNEL_ID


fun NotificationManager.sendNotification(
    extras: MutableSet<Data>,
    applicationContext: Context
) {

    val style = NotificationCompat.InboxStyle()
    val contentIntent = Intent(applicationContext, MainActivity::class.java)

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val detailIntent = Intent(applicationContext, DetailActivity::class.java)

    extras.forEach {
        detailIntent.putExtra(it.key, it.value)
    }

    val detailPendingIntent = PendingIntent.getActivity(
        applicationContext,
        DETAIL_NOTIFICATION_ID,
        detailIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        CHANNEL_ID
    )
        .setContentIntent(detailPendingIntent)
        .setSmallIcon(R.drawable.ic_baseline_notifications_none_24)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(applicationContext.getString(R.string.notification_description))
        .setStyle(style)
        .addAction(
            R.drawable.ic_baseline_notifications_none_24,
            applicationContext.getString(R.string.notification_button),
            detailPendingIntent
        )
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}

const val NOTIFICATION_ID = 100
const val DETAIL_NOTIFICATION_ID = 200
const val DOWNLOAD_STATUS = "downloadStatus"
const val DOWNLOAD_URI = "downloadUri"

data class Data(
    val key: String,
    val value: String
)