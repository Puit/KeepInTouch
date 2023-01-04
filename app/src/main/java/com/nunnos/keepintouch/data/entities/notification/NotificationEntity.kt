package com.nunnos.keepintouch.data.entities.notification

import com.google.gson.annotations.SerializedName

class NotificationEntity {
    enum class PeriodicityType(val value: String) {
        WEEKLY("WEEKLY"),
        MONTHLY("MONTHLY")
    }

    @SerializedName("id")
    var id: Int = 1

    @SerializedName("periodicity")
    var periodicity: PeriodicityType = PeriodicityType.WEEKLY // Monthly, weekly

    @SerializedName("periodicityList")
    var periodicityList: List<Boolean> =
        listOf<Boolean>() // (mon,wed,fri) -> (true,false,true,false,true,false,false)

    @SerializedName("dayOfTheMonth")
    var dayOfTheMonth: Int = 0 // just if monthly

    @SerializedName("title")
    var title: String = ""

    @SerializedName("message")
    var message: String = ""

    @SerializedName("hour")
    var hour: Int = 0

    @SerializedName("minute")
    var minute: Int = 0
    //TODO: MONTHLY
    // si es selecciona un dia major del 28, comprobar si el mes en aquell any te aquell dia, si es així programar alarma, de no ser
    //així programarla per l'ultim dia del mes
}