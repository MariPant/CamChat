package com.app.camchat.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.camchat.MainActivity
import com.app.camchat.R
import com.app.camchat.viewModel.MainViewModel
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.android.ext.android.inject

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val viewModel: MainViewModel by inject()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        remoteMessage.data["https://doorcamera-f8a21-default-rtdb.europe-west1.firebasedatabase.app/"]?.let { imageUrl ->
            viewModel.addBotMessage(imageUrl)
            sendNotification("New face detected", imageUrl)
        }
    }

    private fun sendNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val channelId = "default_channel"
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.warning)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create the notification channel if on API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Check for POST_NOTIFICATIONS permission
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(this).notify(0, builder.build())
        }
    }
}