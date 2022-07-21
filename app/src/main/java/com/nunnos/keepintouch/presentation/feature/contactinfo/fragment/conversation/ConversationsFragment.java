package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.conversation;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.base.baseviewmodel.EmptyViewModel;
import com.nunnos.keepintouch.databinding.FragmentConversationsBinding;
import com.nunnos.keepintouch.domain.model.Conversation;
import com.nunnos.keepintouch.presentation.component.recyclerviews.contactcard.RVContactCardAdapter;
import com.nunnos.keepintouch.presentation.component.recyclerviews.conversationcard.RVConversationCardAdapter;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;

import java.util.List;

public class ConversationsFragment extends BaseFragmentViewModelLiveData<EmptyViewModel, ContactInfoViewModel, FragmentConversationsBinding> {

    private RVConversationCardAdapter adapter;
    public ConversationsFragment() {
        //Required empty public constructor
    }

    public static ConversationsFragment newInstance() {
        ConversationsFragment fragment = new ConversationsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shareViewModel.retrieveConversations(getContext());
        initObservers();
        initListeners();
    }

    private void initObservers() {
        shareViewModel.getConversations().observe(getViewLifecycleOwner(), this::onConversationsReceived);
    }

    private void onConversationsReceived(List<Conversation> conversations) {
        setView();
    }

    private void setView() {
        adapter = new RVConversationCardAdapter(shareViewModel.getConversations().getValue());
        databinding.conversationsRecyclerView.setAdapter(adapter);
        databinding.conversationsRecyclerView.setHasFixedSize(false);
    }

    private void initListeners() {
        databinding.conversationsAddButton.setOnClickListener(v -> {
            shareViewModel.showNewConversationFragment();
        });
    }

    //Region Base Methods

    @Override
    protected int layout() {
        return R.layout.fragment_conversations;
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
