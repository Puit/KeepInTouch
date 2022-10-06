package com.nunnos.keepintouch.presentation.feature.main.fragment.main;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.base.baseviewmodel.EmptyViewModel;
import com.nunnos.keepintouch.databinding.FragmentMainBinding;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.presentation.component.CustomEditText;
import com.nunnos.keepintouch.presentation.component.recyclerviews.contactcard.RVContactCardAdapter;
import com.nunnos.keepintouch.presentation.component.recyclerviews.searchcard.RVSearchCardAdapter;
import com.nunnos.keepintouch.presentation.feature.main.activity.vm.MainViewModel;

import java.util.List;

public class MainFragment extends BaseFragmentViewModelLiveData<MainViewModel, FragmentMainBinding> {

    private RVContactCardAdapter adapter;

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
        shareViewModel.retrieveLastIndex(getContext());
        initObservers();
        initListeners();
    }

    private void initObservers() {
        shareViewModel.getContacts().observe(getViewLifecycleOwner(), this::onContactsReceived);
    }

    private void onContactsReceived(List<Contact> contactEntities) {
        setNormalView(contactEntities);
    }

    private void initListeners() {
        databinding.mainAddImageButton.setOnClickListener(v -> shareViewModel.navigateToNewContact());
        databinding.mainSearcher.setListener(() -> shareViewModel.navigateToSearchContact());
    }

    private void setNormalView(List<Contact> contactList) {
        if (contactList.size() < 1) {
            databinding.mainSearcher.setVisibility(View.GONE);
            databinding.mainNoContactTv.setVisibility(View.VISIBLE);
        } else {
            databinding.mainSearcher.setVisibility(View.VISIBLE);
            databinding.mainNoContactTv.setVisibility(View.GONE);
            adapter = new RVContactCardAdapter(contactList);
            databinding.mainRecyclerView.setAdapter(adapter);
            databinding.mainRecyclerView.setHasFixedSize(false);
        }
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