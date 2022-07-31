package com.nunnos.keepintouch.presentation.feature.contactinfo;

import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.CONTACT_INFO;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.CONTACT_PERSONAL_DATA;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.CONVERSATIONS;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.NEW_CONVERSATION;

import androidx.annotation.NonNull;

import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.ContactInfoActivity;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.ContactInfoFragment;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.ContactPersonalDataFragment;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.conversation.ConversationsFragment;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.conversation.NewConversationFragment;

public abstract class ContactInfoNavigationManager {
    private ContactInfoNavigationManager() {
        // not required
    }

    public static void goTo(@NonNull ContactInfoActivity activity, @ContactInfoNavigation Integer navigation, ContactInfoViewModel viewModel) {
        switch (navigation) {
            case CONVERSATIONS:
                navigateToChats(activity);
                break;
            case CONTACT_INFO:
                navigateToContactInfo(activity);
                break;
            case CONTACT_PERSONAL_DATA:
                navigateToContactPersonalData(activity);
                break;
            case NEW_CONVERSATION:
                showNewConversation(activity);
                break;
            default:
                throw new IllegalStateException("ContactInfoNavigationManager error, navigation has not been implementad");
        }
    }

    private static void navigateToChats(ContactInfoActivity activity) {
        activity.overrideSlidingUpTransition(ConversationsFragment.newInstance());
    }

    private static void navigateToContactInfo(ContactInfoActivity activity) {
        activity.overrideSlidingUpTransition(ContactInfoFragment.newInstance());
    }

    private static void navigateToContactPersonalData(ContactInfoActivity activity) {
        activity.overrideSlidingUpTransition(ContactPersonalDataFragment.newInstance());
    }

    private static void showNewConversation(ContactInfoActivity activity) {
        activity.overrideSlidingUpTransition(NewConversationFragment.newInstance());
    }
}
