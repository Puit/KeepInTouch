package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.databinding.FragmentContactInfoBinding;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.vm.ContactInfoFragmentViewModel;

public class ContactInfoFragment extends BaseFragmentViewModelLiveData<ContactInfoFragmentViewModel, ContactInfoViewModel, FragmentContactInfoBinding> {

    public ContactInfoFragment() {
        //Required empty public constructor
    }

    public static ContactInfoFragment newInstance() {
        ContactInfoFragment fragment = new ContactInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
    }


    private void initListeners() {
    }

    //Region Base Methods

    @Override
    protected int layout() {
        return R.layout.fragment_contact_info;
    }

    @Override
    protected void dataBindingViewModel() {
        databinding.setShareviewmodel(shareViewModel);
    }

    @Override
    protected boolean isShareViewModel() {
        return true;
    }
}
