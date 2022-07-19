package com.nunnos.keepintouch.presentation.feature.main;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        MainNavigation.MAIN,
        MainNavigation.NEW_CONTACT,
        MainNavigation.CONTACT_INFO
})
@Retention(RetentionPolicy.SOURCE)
public @interface MainNavigation {
    int MAIN = 0;
    int NEW_CONTACT = 1;
    int CONTACT_INFO = 2;
}
