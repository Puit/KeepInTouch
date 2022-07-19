package com.nunnos.keepintouch.base.baseviewmodel;

import androidx.lifecycle.LifecycleObserver;

import com.nunnos.keepintouch.base.baseview.base.viewmodel.BaseViewModel;

public class EmptyViewModel extends BaseViewModel implements LifecycleObserver {

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onCleared() {

    }
}
