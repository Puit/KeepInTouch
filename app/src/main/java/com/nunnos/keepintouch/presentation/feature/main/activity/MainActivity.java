package com.nunnos.keepintouch.presentation.feature.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseActivityViewModelLiveData;
import com.nunnos.keepintouch.databinding.ActivityMainBinding;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.presentation.feature.main.MainNavigationManager;
import com.nunnos.keepintouch.presentation.feature.main.activity.vm.MainViewModel;
import com.nunnos.keepintouch.presentation.feature.main.fragment.MainFragment;
import com.nunnos.keepintouch.utils.CameraUtils;

import static com.nunnos.keepintouch.utils.Constants.REQUEST_IMAGE_CAPTURE;

public class MainActivity extends BaseActivityViewModelLiveData<MainViewModel, ActivityMainBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        initObservers();
        redirectTo();
    }

    private void setView() {

    }

    private void initObservers() {
        viewModel.getNavigation().observe(this, navigation -> MainNavigationManager.goTo(this, navigation, viewModel));
    }

    private Context getFragmentContext() {
        return dataBinding.activityFragmentContainer.getContext();
    }

    public void setBackgroundColorToNewContact(int backgroundColor) {
        Contact contact = viewModel.getNewContact();
        contact.setBgColor(backgroundColor);
        viewModel.setNewContact(contact);
    }

    private void redirectTo() {
        viewModel.navigateToMain();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Data es null porque la imagen se guarda en storage

            //viewModel.setNewContactBitmap(CameraUtils.getImageFromResult(requestCode, resultCode, data));
        }
    }

    /*******************************************
     * IMPLEMENTATION BASE CLASS
     * *****************************************/
    @Override
    protected int layout() {
        return R.layout.activity_main;
    }

    @Override
    protected void dataBindingViewModel() {
        viewModel = new MainViewModel();
        dataBinding.setShareviewmodel(viewModel);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_fragment_container);
        if (fragment instanceof MainFragment) {
            this.finishAndRemoveTask(); //TODO: matarà las notificaciones?
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected Class<MainViewModel> getClassViewModel() {
        return MainViewModel.class;
    }
}