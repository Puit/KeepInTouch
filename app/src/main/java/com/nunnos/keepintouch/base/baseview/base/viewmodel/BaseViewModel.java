package com.nunnos.keepintouch.base.baseview.base.viewmodel;

import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel  extends ViewModel {
    public BaseViewModel(){
    }
    public abstract void resume();
    public abstract void pause();
    public abstract void destroy();
    public abstract void onCleared();
}
