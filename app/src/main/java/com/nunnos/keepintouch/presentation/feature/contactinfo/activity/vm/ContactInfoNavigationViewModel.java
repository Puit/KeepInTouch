package com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm;

import androidx.lifecycle.MutableLiveData;

import com.nunnos.keepintouch.base.baseview.base.viewmodel.BaseViewModel;
import com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation;

public class ContactInfoNavigationViewModel extends BaseViewModel {

    protected MutableLiveData<Integer> navigation = new MutableLiveData<>();

    public MutableLiveData<Integer> getNavigation() {
        return navigation;
    }

    public void navigateToChat() {
        navigation.setValue(ContactInfoNavigation.CHATS);
    }

    public void navigateToContactInfo() {
        navigation.setValue(ContactInfoNavigation.CONTACT_INFO);
    }

    public void navigateToContactPersonalData() {
        navigation.setValue(ContactInfoNavigation.CONTACT_PERSONAL_DATA);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onCleared() {

    }
}
