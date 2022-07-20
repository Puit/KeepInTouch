package com.nunnos.keepintouch.presentation.feature.main;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.ContactInfoActivity;
import com.nunnos.keepintouch.presentation.feature.main.activity.MainActivity;
import com.nunnos.keepintouch.presentation.feature.main.activity.vm.MainViewModel;
import com.nunnos.keepintouch.presentation.feature.main.fragment.main.MainFragment;
import com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.NewContactFragment;

import static com.nunnos.keepintouch.presentation.feature.main.MainNavigation.CONTACT_INFO;
import static com.nunnos.keepintouch.presentation.feature.main.MainNavigation.MAIN;
import static com.nunnos.keepintouch.presentation.feature.main.MainNavigation.NEW_CONTACT;

public class MainNavigationManager {
    private MainNavigationManager() {
        // not required
    }

    public static void goTo(@NonNull MainActivity activity, @MainNavigation Integer navigation, MainViewModel viewModel) {
        switch (navigation) {
            case MAIN:
                navigateToMain(activity);
                break;
            case NEW_CONTACT:
                navigateToNewContact(activity);
                break;
            case CONTACT_INFO:
                navigateToContactInfo(activity);
                break;
            default:
                throw new IllegalStateException("ContactInfoNavigationManager error, navigation has not been implementad");
        }
    }

    private static void navigateToMain(MainActivity activity) {
        activity.overrideSlidingUpTransition(MainFragment.newInstance());
    }

    private static void navigateToNewContact(MainActivity activity) {
        activity.overrideSlidingUpTransition(NewContactFragment.newInstance());
    }

    private static void navigateToContactInfo(MainActivity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), ContactInfoActivity.class);
//        intent.putParcelableArrayListExtra( ..., ...);
        activity.launchSlidingUpActivity(intent);
    }
}
