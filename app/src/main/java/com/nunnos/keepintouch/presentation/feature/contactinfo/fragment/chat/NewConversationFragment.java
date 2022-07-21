package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.chat;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.base.baseviewmodel.EmptyViewModel;
import com.nunnos.keepintouch.data.CustomDate;
import com.nunnos.keepintouch.databinding.FragmentNewConversationBinding;
import com.nunnos.keepintouch.domain.model.Conversation;
import com.nunnos.keepintouch.presentation.component.CustomSwitch;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.dialogs.DatePickerFragment;

public class NewConversationFragment extends BaseFragmentViewModelLiveData<EmptyViewModel, ContactInfoViewModel, FragmentNewConversationBinding> {

    private Conversation newConversation = new Conversation();

    public NewConversationFragment() {
        //Required empty public constructor
    }

    public static NewConversationFragment newInstance() {
        NewConversationFragment fragment = new NewConversationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView();
        setListeners();
    }

    private void setView() {
        databinding.ncIsImportant.setIsRightClicked(false);
    }

    private void setListeners() {
        databinding.ncButton.setOnClickListener(v -> {
            newConversation.addContact(shareViewModel.getContact().getValue().getId());
            newConversation.setChat(databinding.ncConversation.getText());
            shareViewModel.addNewConversation(getContext(), newConversation);
            shareViewModel.navigateToConversation();
        });
        databinding.ncDate.setListener(this::showDatePickerDialog);
        databinding.ncIsImportant.setListener(new CustomSwitch.CustomListener() {
            @Override
            public void onLeft() {
                newConversation.setImportant(false);
            }

            @Override
            public void onRight() {
                newConversation.setImportant(true);
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            CustomDate date = new CustomDate(day, month, year);
            final String selectedDate = date.toString();
            newConversation.setDate(selectedDate);
            databinding.ncDate.setText(selectedDate);
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    @Override
    protected int layout() {
        return R.layout.fragment_new_conversation;
    }

    @Override
    protected void dataBindingViewModel() {
        databinding.setShareviewmodel(shareViewModel);
    }

    @Override
    protected void provideViewModel(Class viewModelClass) {
        super.provideViewModel(viewModelClass);
    }

    @Override
    protected boolean isShareViewModel() {
        return true;
    }
}
