package com.nunnos.keepintouch.presentation.feature.contactinfo.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseActivityViewModelLiveData;
import com.nunnos.keepintouch.databinding.ActivityContactInfoBinding;
import com.nunnos.keepintouch.presentation.component.BottomMenu;
import com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigationManager;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;

public class ContactInfoActivity extends BaseActivityViewModelLiveData<ContactInfoViewModel, ActivityContactInfoBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        initObservers();
        redirectTo();
    }

    private void setView() {
        dataBinding.contactInfoActivityBottomMenu.setListener(new BottomMenu.ButtonsListeners() {
            @Override
            public void onButton1Click() {
                //Do nothing
            }

            @Override
            public void onButton2Click() {
                viewModel.navigateToChat();
            }

            @Override
            public void onButton3Click() {
                viewModel.navigateToContactInfo();
            }

            @Override
            public void onButton4Click() {
                viewModel.navigateToContactPersonalData();
            }
        });
    }

    private void initObservers() {
        viewModel.getNavigation().observe(this, navigation -> ContactInfoNavigationManager.goTo(this, navigation, viewModel));
    }

    private void redirectTo() {
        viewModel.navigateToChat();
        dataBinding.contactInfoActivityBottomMenu.setItemSelected(2); // Marca el botón de chat
    }

    /*******************************************
     * IMPLEMENTATION BASE CLASS
     * *****************************************/
    @Override
    protected int layout() {
        return R.layout.activity_contact_info;
    }

    @Override
    protected void dataBindingViewModel() {
        viewModel = new ContactInfoViewModel();
        dataBinding.setShareviewmodel(viewModel);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected Class<ContactInfoViewModel> getClassViewModel() {
        return ContactInfoViewModel.class;
    }
}
