package com.nunnos.keepintouch.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.nunnos.keepintouch.notifications.Notification.Companion.scheduleDailyNotification

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            scheduleDailyNotification(context)
        }
    }

}