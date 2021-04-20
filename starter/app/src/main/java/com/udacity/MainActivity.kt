package com.udacity

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        custom_button.setOnClickListener {
            download()
        }

    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            val notificationManager = ContextCompat.getSystemService(
                applicationContext,
                NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(createChannel(
                CHANNEL_ID,
                getString(R.string.notification_channel_name)
            ))
            notificationManager.sendNotification(getString(R.string.notification_description),applicationContext)
        }
    }

    private fun download() {
        var url = ""

        if (raddioGroup.checkedRadioButtonId == -1) {
            Toast.makeText(this, getString(R.string.button_info_text), Toast.LENGTH_SHORT).show()
        } else {
            when (raddioGroup.checkedRadioButtonId) {
                R.id.optionOne -> url = URL_OPTION_ONE
                R.id.optionTwo -> url = URL_OPTION_TWO
                R.id.optionThree -> url = URL_OPTION_THREE
            }
            val request =
                DownloadManager.Request(Uri.parse(url))
                    .setTitle(getString(R.string.app_name))
                    .setDescription(getString(R.string.app_description))
                    .setRequiresCharging(false)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true)

            val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            downloadID =
                downloadManager.enqueue(request)// enqueue puts the download request in the queue.


        }

    }

    fun createChannel(channelId: String, channelName: String) : NotificationChannel {
      return NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
    }

    companion object {
        private const val URL_OPTION_TWO =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val URL_OPTION_ONE = "https://github.com/bumptech/glide"
        private const val URL_OPTION_THREE = "https://github.com/square/retrofit"
         const val CHANNEL_ID = "channelId"
    }

}
