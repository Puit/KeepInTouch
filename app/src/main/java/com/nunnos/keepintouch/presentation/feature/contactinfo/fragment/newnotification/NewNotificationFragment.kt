package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.newnotification

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.nunnos.keepintouch.R
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData
import com.nunnos.keepintouch.data.entities.notification.NotificationEntity
import com.nunnos.keepintouch.data.entities.notification.NotificationsEntityManager
import com.nunnos.keepintouch.data.entities.notification.NotificationsEntityManager.Companion.getNotification
import com.nunnos.keepintouch.databinding.FragmentNewNotificationBinding
import com.nunnos.keepintouch.presentation.component.CircleTextInsideRadiobutton
import com.nunnos.keepintouch.presentation.component.WeekDayPicker
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel
import com.nunnos.keepintouch.utils.AlertsManager
import com.nunnos.keepintouch.utils.AlertsManager.TwoButtonsAlertListener
import com.nunnos.keepintouch.utils.TextUtils


class NewNotificationFragment :
    BaseFragmentViewModelLiveData<ContactInfoViewModel, FragmentNewNotificationBinding>() {

    companion object {
        fun newInstance(): NewNotificationFragment {
            val fragment = NewNotificationFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        setListeners()
        shareViewModel.retrieveContactNotification(context)
    }

    private fun onNotificationEntityRecived(notification: NotificationEntity?) {
        if (notification == null) return
        databinding.newNotificationWeek.setStatusWeekStartingOnMonday(
            booleanListToStatusCircleList(
                notification.periodicityList
            )
        )
        initTimePicker(notification.hour, notification.minute)
        databinding.newNotificationTimeMessage.text = notification.message
        databinding.newNotificationDeleteButton.visibility = VISIBLE
    }

    private fun booleanListToStatusCircleList(periodicityList: List<Boolean>): List<CircleTextInsideRadiobutton.Status> {
        var statusList: ArrayList<CircleTextInsideRadiobutton.Status> = ArrayList()
        periodicityList.forEach {
            if (it)
                statusList.add(CircleTextInsideRadiobutton.Status.SELECTED)
            else
                statusList.add(CircleTextInsideRadiobutton.Status.UNSELECTED)
        }
        return statusList
    }

    private fun setView() {
        if (TextUtils.isEmpty(shareViewModel.thisContact.value?.notification)) {
            initTimePicker(8, 0)
            initText()
            databinding.newNotificationDeleteButton.visibility = GONE
        } else {
            val notification =
                getNotification(context, shareViewModel.thisContact.value?.notification!!.toInt())
            initTimePicker(
                notification?.hour ?: 8,
                notification?.minute ?: 0
            )
            databinding.newNotificationTimeMessage.text =
                notification?.message
            val listOfDays =
                getStatusFromPeriodicity(notification?.periodicityList)
            databinding.newNotificationWeek.setStatusWeekStartingOnMonday(listOfDays)
            databinding.newNotificationDeleteButton.visibility = VISIBLE
        }
    }


    private fun initTimePicker(hour: Int, minute: Int) {
        databinding.newNotificationTimePicker.setIs24HourView(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            databinding.newNotificationTimePicker.hour = hour
            databinding.newNotificationTimePicker.minute = minute
        } else {
            databinding.newNotificationTimePicker.currentHour = hour
            databinding.newNotificationTimePicker.currentMinute = minute
        }
    }

    private fun initText() {
        databinding.newNotificationTimeMessage.text =
            getString(R.string.notification_base_text_with_space) + " " + databinding.shareviewmodel?.thisContact?.value?.name
    }

    private fun setListeners() {
        setSaveButton()
        setDeleteButton()
    }

    private fun setSaveButton() {
        databinding.newNotificationSaveButton.setOnClickListener {
            if (databinding.newNotificationWeek.isAnyDaySelected()) {
                shareViewModel.updateThisContactNotification(context, createNotificationFromForm())
                activity?.onBackPressed()
            } else {
                AlertsManager.showOneButtonAlert(
                    activity,
                    null,
                    "At least one day must be selected",
                    "Accept",
                    true
                )
            }
        }
    }

    private fun setDeleteButton() {
        databinding.newNotificationDeleteButton.setOnClickListener {
            val listener: TwoButtonsAlertListener = object : TwoButtonsAlertListener {
                override fun onLeftClick() {
                    shareViewModel.deleteNotificationBroadcast(
                        context,
                        createNotificationFromForm()
                    )
                    activity?.onBackPressed()
                }

                override fun onRightClick() {
                    //Do nothing
                }
            }
            AlertsManager.showTwoButtonsAlert(
                activity,
                listener,
                "Are you sure you want to delete this alarm?",
                "Delete",
                "Cancel",
                true
            )
        }
    }

    private fun createNotificationFromForm(): NotificationEntity {
        val notification = NotificationEntity()

        if (Build.VERSION.SDK_INT >= 23) {
            notification.hour = databinding.newNotificationTimePicker.hour
            notification.minute = databinding.newNotificationTimePicker.minute
        } else {
            notification.hour = databinding.newNotificationTimePicker.currentHour
            notification.minute = databinding.newNotificationTimePicker.currentMinute
        }
        notification.periodicity = NotificationEntity.PeriodicityType.WEEKLY
        notification.title = databinding.shareviewmodel?.thisContact?.value?.fullName ?: ""
        notification.message = databinding.newNotificationTimeMessage.text
        notification.periodicityList =
            getPeriodicityFromWeekDayPicker(databinding.newNotificationWeek)
        notification.id = NotificationsEntityManager.getNextId(context)
        return notification
    }

    private fun getStatusFromPeriodicity(periodicity: List<Boolean>?): List<CircleTextInsideRadiobutton.Status> {
        if (periodicity != null) {
            var result = mutableListOf<CircleTextInsideRadiobutton.Status>()

            periodicity.forEach {
                if (it) {
                    result.add(CircleTextInsideRadiobutton.Status.SELECTED)
                } else {
                    result.add(CircleTextInsideRadiobutton.Status.UNSELECTED)

                }
            }
            return result
        }
        return listOf(
            CircleTextInsideRadiobutton.Status.UNSELECTED,
            CircleTextInsideRadiobutton.Status.UNSELECTED,
            CircleTextInsideRadiobutton.Status.UNSELECTED,
            CircleTextInsideRadiobutton.Status.UNSELECTED,
            CircleTextInsideRadiobutton.Status.UNSELECTED,
            CircleTextInsideRadiobutton.Status.UNSELECTED,
            CircleTextInsideRadiobutton.Status.UNSELECTED
        )
    }

    private fun getPeriodicityFromWeekDayPicker(weekDayPicker: WeekDayPicker): List<Boolean> {
        val booleanList = ArrayList<Boolean>()
        weekDayPicker.getStatusWeekStartingOnMonday().forEach {
            if (it == CircleTextInsideRadiobutton.Status.SELECTED)
                booleanList.add(true)
            else
                booleanList.add(false)
        }
        return booleanList
    }

    override fun layout(): Int {
        return R.layout.fragment_new_notification
    }

    override fun dataBindingViewModel() {
        databinding.shareviewmodel = shareViewModel
    }

    override fun isShareViewModel(): Boolean {
        return true
    }
}