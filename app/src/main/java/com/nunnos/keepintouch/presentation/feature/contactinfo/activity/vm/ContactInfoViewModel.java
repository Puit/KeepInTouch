package com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm;

import androidx.lifecycle.LifecycleObserver;

public class ContactInfoViewModel extends ContactInfoNavigationViewModel implements LifecycleObserver {
    private int contador = 0;

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
}
