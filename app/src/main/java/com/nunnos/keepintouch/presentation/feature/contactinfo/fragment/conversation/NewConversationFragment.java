package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.conversation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.base.baseviewmodel.EmptyViewModel;
import com.nunnos.keepintouch.data.CustomDate;
import com.nunnos.keepintouch.databinding.FragmentNewConversationBinding;
import com.nunnos.keepintouch.presentation.component.CustomSwitch;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.dialogs.DatePickerFragment;
import com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.dialogs.TimePickerFragment;

import static com.nunnos.keepintouch.utils.Constants.REQUEST_SELECT_IMAGE;

public class NewConversationFragment extends BaseFragmentViewModelLiveData<EmptyViewModel, ContactInfoViewModel, FragmentNewConversationBinding> {

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
        initObservers();
        setListeners();
    }

    private void setView() {
        databinding.ncIsImportant.setIsRightClicked(true);
    }

    private void initObservers() {
        shareViewModel.getNewConversationBitmap().observe(getActivity(), this::setPhotoToImageView);
    }

    private void setPhotoToImageView(Bitmap bitmap) {
        databinding.ncImage.setImageBitmap(bitmap);
    }

    private void setListeners() {
        databinding.ncButton.setOnClickListener(v -> {
            shareViewModel.getNewConversation().addContact(shareViewModel.getContact().getValue().getId());
            shareViewModel.getNewConversation().setChat(databinding.ncConversation.getText().toString());
            shareViewModel.addNewConversation(getContext(), shareViewModel.getNewConversation());
            shareViewModel.navigateToConversation();
        });
        databinding.ncDate.setListener(this::showDatePickerDialog);
        databinding.ncTime.setListener(this::showTimePickerDialog);
        databinding.ncIsImportant.setListener(new CustomSwitch.CustomListener() {
            @Override
            public void onLeft() {
                shareViewModel.getNewConversation().setImportant(true);
            }

            @Override
            public void onRight() {
                shareViewModel.getNewConversation().setImportant(false);
            }
        });
        databinding.ncAddImageButton.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, REQUEST_SELECT_IMAGE);
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            CustomDate date = new CustomDate(day, month, year);
            final String selectedDate = date.toString();
            shareViewModel.getNewConversation().setDate(selectedDate);
            databinding.ncDate.setText(selectedDate);
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");

    }

    private void showTimePickerDialog() {
        TimePickerFragment newFragment = TimePickerFragment.newInstance((timePicker, hour, minute) -> {
            String time = hour + ":" + minute;
            shareViewModel.getNewConversation().setDate(time);
            databinding.ncDate.setText(time);
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
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
