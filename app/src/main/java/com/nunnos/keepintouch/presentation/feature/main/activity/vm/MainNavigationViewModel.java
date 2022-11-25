package com.nunnos.keepintouch.presentation.feature.main.activity.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nunnos.keepintouch.presentation.feature.main.MainNavigation;

public class MainNavigationViewModel extends ViewModel {

    protected MutableLiveData<Integer> navigation = new MutableLiveData<>();

    public MutableLiveData<Integer> getNavigation() {
        return navigation;
    }

    public void navigateToMain() {
        navigation.setValue(MainNavigation.MAIN);
    }
    public void navigateToNewContact() {
        navigation.setValue(MainNavigation.NEW_CONTACT);
    }
    public void navigateToContactInfo() {
        navigation.setValue(MainNavigation.CONTACT_INFO);
    }

    public void navigateToSearchContact() {
        navigation.setValue(MainNavigation.SEARCH_CONTACT);
    }
}
