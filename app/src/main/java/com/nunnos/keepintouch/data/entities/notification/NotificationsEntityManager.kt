package com.nunnos.keepintouch.data.entities.notification

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.nunnos.keepintouch.R
import com.nunnos.keepintouch.notifications.Notification.Companion.scheduleCallReminderNotification
import com.nunnos.keepintouch.utils.Constants.CONTACTS_SEPARATOR
import com.nunnos.keepintouch.utils.TextUtils
import java.util.*


class NotificationsEntityManager {
    companion object {
        var LAST_ID = "LAST_ID"
        var ALL_IDS = "ALL_IDS"

        @JvmStatic
        fun getNotification(context: Context?, id: Int): NotificationEntity? {
            val sharedPref: SharedPreferences =
                context!!.getSharedPreferences(context.getString(R.string.app_name), MODE_PRIVATE)
            if (sharedPref.getString(id.toString(), "") == null) return null

            return notificationFromJson(sharedPref.getString(id.toString(), ""))
        }

        @JvmStatic
        fun saveNotification(context: Context?, notification: NotificationEntity) {
            val sharedPref: SharedPreferences =
                context!!.getSharedPreferences(context.getString(R.string.app_name), MODE_PRIVATE)
            val prefsEditor: SharedPreferences.Editor = sharedPref.edit()
            val json = notificationToJson(notification)
            prefsEditor.putString(notification.id.toString(), json)
            prefsEditor.apply()
            addIdToAllNotificationsList(sharedPref, notification.id)
        }

        @JvmStatic
        fun deleteNotification(context: Context?, notification: NotificationEntity) {
            val sharedPref: SharedPreferences =
                context!!.getSharedPreferences(context.getString(R.string.app_name), MODE_PRIVATE)
            val prefsEditor: SharedPreferences.Editor = sharedPref.edit()
            prefsEditor.remove(notification.id.toString())
            prefsEditor.apply()
            deleteIdFromAllNotificationsList(sharedPref, notification.id)
        }

        @JvmStatic
        fun getNextId(context: Context?): Int {
            val sharedPref: SharedPreferences =
                context!!.getSharedPreferences(context.getString(R.string.app_name), MODE_PRIVATE)
            val lastID = (sharedPref.getInt(
                LAST_ID,
                10
            ) + 1) //Nos saltamos 10 posiciones que las guardamos para otro tipo de notificaciones
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.putInt(LAST_ID, lastID)
            editor.apply()
            return lastID
        }

        @JvmStatic
        fun resetAllReminderAlarms(context: Context?) {
            getAllNotifications(context).forEach {
                if (context != null) {
                    scheduleCallReminderNotification(context, it)
                }
            }
        }

        @JvmStatic
        private fun addIdToAllNotificationsList(sharedPref: SharedPreferences, id: Int) {
            var idsString = sharedPref.getString(ALL_IDS, "")
            if (!(idsString?.contains(CONTACTS_SEPARATOR + id + CONTACTS_SEPARATOR))!!) {
                val prefsEditor: SharedPreferences.Editor = sharedPref.edit()
                idsString = idsString + CONTACTS_SEPARATOR + id
                prefsEditor.putString(ALL_IDS, idsString)
                prefsEditor.apply()
            }
        }

        @JvmStatic
        private fun deleteIdFromAllNotificationsList(sharedPref: SharedPreferences, id: Int) {
            var idsString = sharedPref.getString(ALL_IDS, "")
            if (idsString?.contains(CONTACTS_SEPARATOR + id)!!) {
                val prefsEditor: SharedPreferences.Editor = sharedPref.edit()
                idsString = idsString.replace(CONTACTS_SEPARATOR + id.toString(), "")
                prefsEditor.putString(ALL_IDS, idsString)
                prefsEditor.apply()
            }
        }

        @JvmStatic
        private fun getAllNotifications(context: Context?): List<NotificationEntity> {
            val ids = getAllIds(context)
            val notifications = ArrayList<NotificationEntity>()
            ids.forEach {
                val notification = getNotification(context, it)
                if (notification != null) {
                    notifications.add(notification)
                }
            }
            return notifications
        }

        @JvmStatic
        private fun getAllIds(context: Context?): List<Int> {
            val sharedPref: SharedPreferences =
                context!!.getSharedPreferences(context.getString(R.string.app_name), MODE_PRIVATE)
            val savedString: String? = sharedPref.getString(ALL_IDS, "")
            val st = StringTokenizer(savedString, CONTACTS_SEPARATOR)
            val savedList = ArrayList<Int>()
            for (i in 0 until st.countTokens()) {
                savedList.add(st.nextToken().toInt())
            }
            return savedList
        }

        private fun notificationFromJson(json: String?): NotificationEntity? {
            if (TextUtils.isEmpty(json)) return null
            val gson = Gson()
            return gson.fromJson(json, NotificationEntity::class.java)
        }

        private fun notificationToJson(notification: NotificationEntity): String {
            val gson = Gson()
            return gson.toJson(notification)
        }
    }
}