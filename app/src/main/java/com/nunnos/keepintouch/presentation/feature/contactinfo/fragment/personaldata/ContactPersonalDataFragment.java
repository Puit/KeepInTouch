package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.personaldata;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.databinding.FragmentContactPersonalDataBinding;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.vm.ContactPersonalDataViewModel;

public class ContactPersonalDataFragment extends BaseFragmentViewModelLiveData<ContactPersonalDataViewModel, ContactInfoViewModel, FragmentContactPersonalDataBinding> {

    public ContactPersonalDataFragment() {
        //Required empty public constructor
    }

    public static ContactPersonalDataFragment newInstance() {
        ContactPersonalDataFragment fragment = new ContactPersonalDataFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView();
        initListeners();
    }

    private void initListeners() {
        databinding.contactPersonalDataFavRounder.setOnClickListener(v -> shareViewModel.showEditContactFragment());
    }

    private void setView() {

    }

    //Region Base Methods

    @Override
    protected int layout() {
        return R.layout.fragment_contact_personal_data;
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
