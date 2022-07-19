package com.nunnos.keepintouch.base.baseviewmodel;

import android.security.identity.ResultData;

import androidx.lifecycle.MutableLiveData;

import com.nunnos.keepintouch.base.baseview.base.viewmodel.BaseViewModel;

public class BaseViewModelLiveData extends BaseViewModel {

    private static String TAG = "BaseViewModelLiveData";
    private MutableLiveData<Boolean> progress = new MutableLiveData<>();
    private MutableLiveData<ResultData> handleError = new MutableLiveData<>();

    @Override
    public void resume() {
        //nothing to do by default
    }

    @Override
    public void pause() {
        //nothing to do by default
    }

    @Override
    public void destroy() {
        //nothing to do by default
    }

    @Override
    public void onCleared() {
        //nothing to do by default
    }
}
