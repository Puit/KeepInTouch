package com.nunnos.keepintouch.base.baseview.base.viewmodel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

public class BaseViewModelFragment<T extends ViewModel> extends Fragment {
    private T viewModel;

    public BaseViewModelFragment() {

    }

    protected void provideViewModel(Class<? extends ViewModel> viewModelClass) {
        try {
            this.viewModel = (T) ViewModelProviders.of(this.getActivity()).get(viewModelClass);
        } catch (Exception var3) {
            throw new NullPointerException("ViewModel can't be null, have you injected the component before?");
        }
    }

    /*    protected void provideViewModel(Class<? extends BaseViewModel> viewModelClass, BaseViewModelFactory factory) {
            try {
                this.viewModel = (T) ViewModelProviders.of(this.getActivity()).get(viewModelClass);
            } catch (Exception var3) {
                throw new NullPointerException("ViewModel can't be null, have you injected the component before?");
            }
        }*/
    protected T getViewModel() {
        return this.viewModel;
    }

    public <D, L extends LiveData<D>> void observe(L liveData, Observer<D> observer) {
        liveData.observe(this, observer);
    }
}
