package com.nunnos.keepintouch.presentation.feature.contactinfo;

import androidx.annotation.NonNull;

import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.ContactInfoActivity;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.ChatsFragment;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.ContactInfoFragment;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.ContactPersonalDataFragment;

import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.CHATS;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.CONTACT_INFO;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.CONTACT_PERSONAL_DATA;

public abstract class ContactInfoNavigationManager {
    private ContactInfoNavigationManager() {
        // not required
    }

    public static void goTo(@NonNull ContactInfoActivity activity, @ContactInfoNavigation Integer navigation, ContactInfoViewModel viewModel) {
        switch (navigation) {
            case CHATS:
                navigateToChats(activity);
                break;
            case CONTACT_INFO:
                navigateToContactInfo(activity);
                break;
            case CONTACT_PERSONAL_DATA:
                navigateToContactPersonalData(activity);
                break;
            default:
                throw new IllegalStateException("ContactInfoNavigationManager error, navigation has not been implementad");
        }
    }

    private static void navigateToChats(ContactInfoActivity activity) {
        activity.overrideSlidingUpTransition(ChatsFragment.newInstance());
    }

    private static void navigateToContactInfo(ContactInfoActivity activity) {
        activity.overrideSlidingUpTransition(ContactInfoFragment.newInstance());
    }

    private static void navigateToContactPersonalData(ContactInfoActivity activity) {
        activity.overrideSlidingUpTransition(ContactPersonalDataFragment.newInstance());
    }

}
