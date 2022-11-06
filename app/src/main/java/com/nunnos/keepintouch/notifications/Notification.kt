package com.nunnos.keepintouch.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.nunnos.keepintouch.R
import com.nunnos.keepintouch.base.baseview.base.App.CHANNEL_ID
import com.nunnos.keepintouch.presentation.feature.main.fragment.main.MainFragment
import com.nunnos.keepintouch.utils.Constants.SMALL_NOTIFICATION_ID

class Notification {
    companion object {
        @JvmStatic
        fun createNotificationChannel(context:Context) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "Channel name"
                val descriptionText = "channel description"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
        @JvmStatic
        fun smallNotification(context: Context, title: String, description: String){
            // Create an explicit intent for an Activity in your app
            val intent = Intent(context, MainFragment::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_IMMUTABLE)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_asexual) //TODO: Cambiar icono por el de la app
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                notify(SMALL_NOTIFICATION_ID, builder.build())
            }
        }
    }
}