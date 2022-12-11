package com.nunnos.keepintouch.presentation.feature.contactinfo;

import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.CONTACT_INFO;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.CONTACT_PERSONAL_DATA;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.CONVERSATIONS;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.EDIT_CONTACT;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.NEW_COMMENT;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.NEW_CONVERSATION;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.NEW_NOTIFICATION;

import androidx.annotation.NonNull;

import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.ContactInfoActivity;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.info.ContactInfoFragment;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.info.NewCommentFragment;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.newnotification.NewNotificationFragment;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.personaldata.ContactPersonalDataFragment;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.conversation.ConversationsFragment;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.conversation.NewConversationFragment;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.personaldata.EditContactFragment;

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
            case EDIT_CONTACT:
                showEditContact(activity);
                break;
            case NEW_COMMENT:
                showNewComment(activity);
                break;
            case NEW_NOTIFICATION:
                showNewNotification(activity);
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
    private static void showEditContact(ContactInfoActivity activity) {
        activity.overrideSlidingUpTransition(EditContactFragment.newInstance());
    }
    private static void showNewComment(ContactInfoActivity activity) {
        activity.overrideSlidingUpTransition(NewCommentFragment.newInstance());
    }
    private static void showNewNotification(ContactInfoActivity activity) {
        activity.overrideSlidingUpTransition(NewNotificationFragment.Companion.newInstance());
    }
}
