package com.nunnos.keepintouch.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.nunnos.keepintouch.R
import com.nunnos.keepintouch.data.entities.notification.NotificationEntity
import com.nunnos.keepintouch.notifications.Notification.Companion.scheduleCallReminderNotification
import com.nunnos.keepintouch.notifications.Notification.Companion.scheduleDailyNotification
import com.nunnos.keepintouch.notifications.Notification.Companion.smallNotification
import com.nunnos.keepintouch.utils.Constants.*
import com.nunnos.keepintouch.utils.TextUtils

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.getIntExtra(EXTRA_NOTIFICATION_TYPE, -1)) {
            Notification.Type.DAILY.value -> showDailyNotification(context, intent)
            Notification.Type.CALL_REMINDER.value -> showCallReminderNotification(context, intent)
            Notification.Type.BIRTHDAY.value -> showBirthdayNotification(context, intent)
        }

    }

    fun showDailyNotification(context: Context, intent: Intent) {
        val title: String? = intent.getStringExtra(DAILY_NOTIFICATION_TITLE)
        val description: String? = intent.getStringExtra(DAILY_NOTIFICATION_DESCRIPTION)
        if (title == null || description == null) {
            smallNotification(context, "title", "It's null")
            return
        }
        //Show the notification
        smallNotification(context, title, description)
        //Schedule new notification for tomorrow
        scheduleDailyNotification(context)
    }

    private fun showCallReminderNotification(context: Context, intent: Intent) {
        val gson = Gson()
        val json: String? = intent.getStringExtra(CALL_REMINDER_NOTIFICATION)
        val notification = gson.fromJson(json, NotificationEntity::class.java)

        if (TextUtils.isEmpty(notification.message)) {
            smallNotification(context, context.getString(R.string.app_name), "")
            return
        }
        //Show the notification
        smallNotification(context, context.getString(R.string.app_name), notification.message)
        //Schedule new notification for next time
        scheduleCallReminderNotification(context, notification)
    }

    private fun showBirthdayNotification(context: Context, intent: Intent) {

    }
}