package com.nunnos.keepintouch.base.baseview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModel;

import com.nunnos.keepintouch.base.baseview.base.viewmodel.BaseViewModel;
import com.nunnos.keepintouch.base.baseview.base.viewmodel.BaseViewModelFragment;

public abstract class BaseFragmentViewModelLiveData<VM extends BaseViewModel & LifecycleObserver, VMS extends ViewModel, DB extends ViewDataBinding> extends BaseViewModelFragment implements LifecycleObserver {
    protected VM viewModel;
    protected VMS shareViewModel;
    protected DB databinding;

    protected abstract @LayoutRes
    int layout();

    protected abstract void dataBindingViewModel();

    protected abstract boolean isShareViewModel();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = (VM) getViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        provideShareViewModel();
        databinding = DataBindingUtil.inflate(inflater, layout(), container, false);
        databinding.setLifecycleOwner(getViewLifecycleOwner());
        dataBindingViewModel();
        return databinding.getRoot();
    }

    @SuppressWarnings("unchecked")
    private void provideShareViewModel() {
        if (isShareViewModel() && isAdded() && getActivity() instanceof BaseActivityViewModelLiveData) {
            shareViewModel = (VMS) ((BaseActivityViewModelLiveData) getActivity()).getShareViewModel();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (viewModel != null) {
            getLifecycle().addObserver(viewModel);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (viewModel != null) {
            getLifecycle().removeObserver(viewModel);
        }
    }

    /**
     * HELPER METHODS
     * */

}
