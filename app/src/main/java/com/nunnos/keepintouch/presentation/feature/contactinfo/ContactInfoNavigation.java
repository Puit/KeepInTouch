package com.nunnos.keepintouch.presentation.feature.contactinfo;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        ContactInfoNavigation.CONVERSATIONS,
        ContactInfoNavigation.CONTACT_INFO,
        ContactInfoNavigation.CONTACT_PERSONAL_DATA,
        ContactInfoNavigation.NEW_CONVERSATION,
        ContactInfoNavigation.EDIT_CONTACT,
        ContactInfoNavigation.NEW_COMMENT
})
@Retention(RetentionPolicy.SOURCE)
public @interface ContactInfoNavigation {
    int CONVERSATIONS = 0;
    int CONTACT_INFO = 1;
    int CONTACT_PERSONAL_DATA = 2;
    int NEW_CONVERSATION = 3;
    int EDIT_CONTACT = 4;
    int NEW_COMMENT = 5;
}
