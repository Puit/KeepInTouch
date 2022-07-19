package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.vm;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nunnos.keepintouch.base.baseview.base.viewmodel.BaseViewModel;

public class ChatsViewModel extends BaseViewModel implements LifecycleObserver {
    private MutableLiveData<String> message = new MutableLiveData<>();

    public void sendMessage(String text) {
        message.setValue(text);
    }

    public LiveData gendMessage(String text) {
        return message;
    }

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
