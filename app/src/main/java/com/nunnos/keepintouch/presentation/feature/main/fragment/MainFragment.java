package com.nunnos.keepintouch.presentation.feature.main.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.base.baseviewmodel.EmptyViewModel;
import com.nunnos.keepintouch.databinding.FragmentMainBinding;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.presentation.component.recyclerviewcontactcard.RVCCAdapter;
import com.nunnos.keepintouch.presentation.feature.main.activity.vm.MainViewModel;

import java.util.List;

public class MainFragment extends BaseFragmentViewModelLiveData<EmptyViewModel, MainViewModel, FragmentMainBinding> {

    private RVCCAdapter adapter;

    public MainFragment() {
        //Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shareViewModel.retrieveContacts(getContext());
        initObservers();
        initListeners();
    }

    private void initObservers() {
        shareViewModel.getContacts().observe(getViewLifecycleOwner(), this::onContactsReceived);
    }

    private void onContactsReceived(List<Contact> contactEntities) {
        Toast.makeText(getContext(), contactEntities.get(0).getName(), Toast.LENGTH_SHORT).show();
        setView();
    }

    private void initListeners() {
        databinding.mainButton.setOnClickListener(v -> shareViewModel.navigateToNewContact());
    }

    private void setView() {
        adapter = new RVCCAdapter(shareViewModel.getContacts().getValue());
        databinding.mainRecyclerView.setAdapter(adapter);
        databinding.mainRecyclerView.setHasFixedSize(false);
    }

    //Region Base Methods
    @Override
    protected int layout() {
        return R.layout.fragment_main;
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