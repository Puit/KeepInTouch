package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.newnotification

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
import com.nunnos.keepintouch.R
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData
import com.nunnos.keepintouch.data.entities.notification.NotificationEntity
import com.nunnos.keepintouch.data.entities.notification.NotificationsEntityManager
import com.nunnos.keepintouch.databinding.FragmentNewNotificationBinding
import com.nunnos.keepintouch.presentation.component.CircleTextInsideRadiobutton
import com.nunnos.keepintouch.presentation.component.WeekDayPicker
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel


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
        initObserver()
        shareViewModel.retrieveContactNotification(context)
    }

    private fun initObserver() {
        shareViewModel.thisContactNotification.observe(
            viewLifecycleOwner
        ) { notification: NotificationEntity? -> this.onNotificationEntityRecived(notification) }
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
        initTimePicker(8, 0)
        initText()
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
        databinding.newNotificationSaveButton.setOnClickListener {
            if (databinding.newNotificationWeek.isAnyDaySelected()) {
                shareViewModel.updateThisContactNotification(context, createNotificationFromForm())
            } else {
                Toast.makeText(
                    context,
                    "At least one day of the week must be selected",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        databinding.newNotificationDeleteButton.setOnClickListener {
            shareViewModel.deleteNotificationBroadcast(context, createNotificationFromForm())
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