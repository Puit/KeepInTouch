package com.nunnos.keepintouch.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.nunnos.keepintouch.R
import com.nunnos.keepintouch.base.baseview.base.App.CHANNEL_ID
import com.nunnos.keepintouch.data.entities.notification.NotificationEntity
import com.nunnos.keepintouch.presentation.feature.main.activity.MainActivity
import com.nunnos.keepintouch.presentation.feature.main.fragment.main.MainFragment
import com.nunnos.keepintouch.utils.Constants.*
import com.nunnos.keepintouch.utils.TextUtils
import java.util.*


class Notification {
    lateinit var alarmManager: AlarmManager
    lateinit var alarmPendingIntent: PendingIntent

    enum class Type(val value: Int) {
        DAILY(0),
        CALL_REMINDER(1),
        BIRTHDAY(2);
    }


    companion object {
        val TAG = "Notification"

        @JvmStatic
        fun createNotificationChannel(context: Context) {
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
        fun smallNotification(context: Context, title: String, description: String) {
            createNotificationChannel(context)
            // Create an explicit intent for an Activity in your app
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                context, 0,
                intent, PendingIntent.FLAG_IMMUTABLE
            )

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo_black)
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

        @JvmStatic
        fun scheduleDailyNotification(context: Context) {
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            val alarmPendingIntent = preparePendingIntentForDailyReminder(
                context,
                DAILY_NOTIFICATION_REQUEST_CODE,
                Type.DAILY,
                context.getString(R.string.daily_notification_title),
                context.getString(R.string.daily_notification_description)
            )

            val calendar = GregorianCalendar.getInstance().apply {
                if (get(Calendar.HOUR_OF_DAY) >= HOUR_TO_SHOW_PUSH) {
                    add(Calendar.DAY_OF_MONTH, 1)
                }

                set(Calendar.HOUR_OF_DAY, HOUR_TO_SHOW_PUSH)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmPendingIntent
            )
        }

        @JvmStatic
        fun scheduleCallReminderNotification(context: Context, notification: NotificationEntity) {
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            val alarmPendingIntent = preparePendingIntentForCallReminder(
                context,
                notification
            )

            if (notification.periodicity == NotificationEntity.PeriodicityType.WEEKLY) {
                prepareWeeklyCallReminderNotification(
                    alarmPendingIntent,
                    alarmManager,
                    notification
                )
            } else {
                prepareMonthlyCallReminderNotification(
                    alarmPendingIntent,
                    alarmManager,
                    notification
                )
            }
        }

        private fun prepareMonthlyCallReminderNotification(
            alarmPendingIntent: Any,
            alarmManager: AlarmManager,
            notification: NotificationEntity
        ) {
            //TODO
        }

        @JvmStatic
        fun cancelCallReminderNotification(context: Context, notification: NotificationEntity) {
            val alarmManager = createAlarmManager(context) ?: return
            val alarmPendingIntent = preparePendingIntentForCallReminder(
                context,
                notification
            )
            alarmManager.cancel(alarmPendingIntent)
        }


        private fun createAlarmManager(context: Context): AlarmManager? {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java)
                if (!alarmManager?.canScheduleExactAlarms()!!) {
                    Intent().also { intent ->
                        intent.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                        context.startActivity(intent)
                        return null
                    }
                }
            }
            return context.getSystemService(ALARM_SERVICE) as AlarmManager
        }

        private fun prepareWeeklyCallReminderNotification(
            alarmPendingIntent: PendingIntent?,
            alarmManager: AlarmManager,
            notification: NotificationEntity
        ) {
            val calendar = getFirstDayOfTheWeekAlarmWillPlay(notification)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    alarmPendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    alarmPendingIntent
                )
            }
            Log.d(
                TAG,
                "prepareWeeklyCallReminderNotification: next alarm: " + TextUtils.dateToString(
                    calendar
                )
            )

        }

        private fun getFirstDayOfTheWeekAlarmWillPlay(notification: NotificationEntity): Calendar {
            val today = Calendar.getInstance()
            val calendar = GregorianCalendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, notification.hour)
                set(Calendar.MINUTE, notification.minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val checkCalendar = GregorianCalendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, notification.hour)
                set(Calendar.MINUTE, notification.minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            var anyDayIsSet = false
            for (i in 6 downTo 0) {
                if (notification.periodicityList[i]) {
                    checkCalendar.set(Calendar.DAY_OF_WEEK, dayOfTheWeekStartingOnSunday(i))
                    checkCalendar[Calendar.DAY_OF_MONTH] // Si no miramos el valor, no se ejecuta
                    if (today > checkCalendar) {
                        if (anyDayIsSet) return calendar

                        val day = calendar.get(Calendar.DAY_OF_MONTH) + 7
                        calendar.set(Calendar.DAY_OF_MONTH, day)
                        checkCalendar.set(Calendar.DAY_OF_MONTH, day)
                        if (calendar < checkCalendar) {
                            return calendar
                        }
                        calendar[Calendar.DAY_OF_MONTH]
                        checkCalendar[Calendar.DAY_OF_MONTH]
                    }

                    calendar.set(Calendar.DAY_OF_WEEK, dayOfTheWeekStartingOnSunday(i))
                    calendar[Calendar.DAY_OF_MONTH]
                    checkCalendar[Calendar.DAY_OF_MONTH]
                    anyDayIsSet = true
                }
            }
            return calendar
        }

        private fun dayOfTheWeekStartingOnSunday(dayStartingOnMonday: Int): Int {
            return when (dayStartingOnMonday) {
                0, 1, 2, 3, 4, 5 -> dayStartingOnMonday + 2
                6 -> Calendar.SUNDAY //1
                else -> dayStartingOnMonday + 2
            }
        }

        private fun prepareMonthlyCallReminderNotification(
            alarmPendingIntent: PendingIntent?,
            alarmManager: AlarmManager,
            notification: NotificationEntity
        ) {
            //TODO
        }

        private fun preparePendingIntentForCallReminder(
            context: Context,
            notification: NotificationEntity
        ): PendingIntent? {
            val intent = Intent(context, AlarmReceiver::class.java)
            val gson = Gson()
            intent.putExtra(CALL_REMINDER_NOTIFICATION, gson.toJson(notification))
            intent.putExtra(EXTRA_NOTIFICATION_TYPE, 1)
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)

            return PendingIntent.getBroadcast(
                context,
                notification.id,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }

        private fun preparePendingIntentForDailyReminder(
            context: Context,
            codeRequest: Int,
            notificationType: Type,
            title: String,
            message: String
        ): PendingIntent? {
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.putExtra(
                DAILY_NOTIFICATION_TITLE,
                title
            )
            intent.putExtra(
                DAILY_NOTIFICATION_DESCRIPTION,
                message
            )
            intent.putExtra(EXTRA_NOTIFICATION_TYPE, notificationType.value)
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)

            return PendingIntent.getBroadcast(
                context,
                codeRequest,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }
    }
}