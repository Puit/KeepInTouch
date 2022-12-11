package com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation;

public class ContactInfoNavigationViewModel extends ViewModel {

    protected MutableLiveData<Integer> navigation = new MutableLiveData<>();

    public MutableLiveData<Integer> getNavigation() {
        return navigation;
    }

    public void navigateToConversation() {
        navigation.setValue(ContactInfoNavigation.CONVERSATIONS);
    }

    public void showNewConversationFragment() {
        navigation.setValue(ContactInfoNavigation.NEW_CONVERSATION);
    }

    public void navigateToContactInfo() {
        navigation.setValue(ContactInfoNavigation.CONTACT_INFO);
    }

    public void navigateToContactPersonalData() {
        navigation.setValue(ContactInfoNavigation.CONTACT_PERSONAL_DATA);
    }

    public void showEditContactFragment() {
        navigation.setValue(ContactInfoNavigation.EDIT_CONTACT);
    }
    public void showNewComment() {
        navigation.setValue(ContactInfoNavigation.NEW_COMMENT);
    }
    public void showNewNotification() {
        navigation.setValue(ContactInfoNavigation.NEW_NOTIFICATION);
    }

}
