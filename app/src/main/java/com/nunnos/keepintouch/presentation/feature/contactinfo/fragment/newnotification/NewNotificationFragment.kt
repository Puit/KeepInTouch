package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.newnotification

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.nunnos.keepintouch.R
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData
import com.nunnos.keepintouch.databinding.FragmentNewNotificationBinding
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
    }

    private fun setView() {
        //TODO("Not yet implemented")
    }

    private fun setListeners() {
        databinding.newNotificationSaveButton.setOnClickListener{
            shareViewModel.thisContact.value!!.notification = databinding.newNotificationWeek.getStatusWeekStartingOnMonday().toString()
        }
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