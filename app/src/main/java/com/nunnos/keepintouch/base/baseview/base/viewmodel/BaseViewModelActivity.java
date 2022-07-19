package com.nunnos.keepintouch.base.baseview.base.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class BaseViewModelActivity<T extends BaseViewModel> extends AppCompatActivity {
    private T viewModel;

    public BaseViewModelActivity() {

    }

    public void onPause() {
        super.onPause();
        if (this.viewModel != null) {
            this.viewModel.pause();
        }
    }

    public void onResume() {
        super.onResume();
        if (this.viewModel != null) {
            this.viewModel.resume();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.viewModel != null) {
            this.viewModel.destroy();
        }
    }

    public void onStart() {
        super.onStart();
    }

    protected void provideViewModel(Class<? extends BaseViewModel> viewModelClass) {
        try {
            this.viewModel = (T) ViewModelProviders.of(this).get(viewModelClass);
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
