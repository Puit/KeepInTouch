package com.nunnos.keepintouch.data.entities.notification

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.nunnos.keepintouch.R


class NotificationsEntityManager {
    companion object {
        var LAST_ID = "LAST_ID"

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
        }

        @JvmStatic
        fun getNextId(context: Context?): Int {
            val sharedPref: SharedPreferences =
                context!!.getSharedPreferences(context.getString(R.string.app_name), MODE_PRIVATE)
            return (sharedPref.getInt(LAST_ID, 10) + 1) //Nos saltamos 10 posiciones que las guardamos para otro tipo de notificaciones
        }

        private fun notificationFromJson(json: String?): NotificationEntity {
            val gson = Gson()
            return gson.fromJson(json, NotificationEntity::class.java)
        }

        private fun notificationToJson(notification: NotificationEntity): String {
            val gson = Gson()
            return gson.toJson(notification)
        }

    }

}