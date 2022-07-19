package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.databinding.FragmentChatsBinding;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.vm.ChatsViewModel;

public class ChatsFragment extends BaseFragmentViewModelLiveData<ChatsViewModel, ContactInfoViewModel, FragmentChatsBinding> {

    public ChatsFragment() {
        //Required empty public constructor
    }

    public static ChatsFragment newInstance() {
        ChatsFragment fragment = new ChatsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView();
        initListeners();
    }

    private void setView() {
        shareViewModel.setContador(shareViewModel.getContador() + 1);
    }

    private void initListeners() {
    }

    //Region Base Methods

    @Override
    protected int layout() {
        return R.layout.fragment_chats;
    }

    @Override
    protected void dataBindingViewModel() {
        databinding.setShareviewmodel(shareViewModel);
    }

    @Override
    protected boolean isShareViewModel() {
        return true;
    }
}
