package com.nunnos.keepintouch.presentation.feature.contactinfo;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        ContactInfoNavigation.CHATS,
        ContactInfoNavigation.CONTACT_INFO,
        ContactInfoNavigation.CONTACT_PERSONAL_DATA
})
@Retention(RetentionPolicy.SOURCE)
public @interface ContactInfoNavigation {
    int CHATS = 0;
    int CONTACT_INFO = 1;
    int CONTACT_PERSONAL_DATA = 2;
}
