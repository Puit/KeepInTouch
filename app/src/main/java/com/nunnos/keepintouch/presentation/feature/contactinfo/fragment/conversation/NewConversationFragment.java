package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.conversation;

import static com.nunnos.keepintouch.utils.Constants.REQUEST_SELECT_IMAGE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.base.baseviewmodel.EmptyViewModel;
import com.nunnos.keepintouch.data.CustomDate;
import com.nunnos.keepintouch.databinding.FragmentNewConversationBinding;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.presentation.component.CustomSwitch;
import com.nunnos.keepintouch.presentation.component.recyclerviews.itemtext.ImageAndText;
import com.nunnos.keepintouch.presentation.component.recyclerviews.itemtext.RVITAdapter;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.dialogs.DatePickerFragment;
import com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.dialogs.TimePickerFragment;
import com.nunnos.keepintouch.utils.FileManager;
import com.nunnos.keepintouch.utils.ImageHelper;

import java.util.ArrayList;
import java.util.List;

public class NewConversationFragment extends BaseFragmentViewModelLiveData<EmptyViewModel, ContactInfoViewModel, FragmentNewConversationBinding> {
    private RVITAdapter genderAdapter;
    private boolean isEdit = false;

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
        shareViewModel.retrieveContacts(getContext());
        setView();
        initObservers();
        setListeners();
    }

    private void setView() {
        if (shareViewModel.getNewConversation() != null) {
            setViewForEdit();
        } else {
            databinding.ncIsImportant.setIsRightClicked(true);
        }
    }

    private void setViewForEdit() {
        isEdit = true;

        databinding.ncConversation.setText(shareViewModel.getNewConversation().getChat());
        databinding.ncDate.setText(shareViewModel.getNewConversation().getDate());
        databinding.ncTime.setText(shareViewModel.getNewConversation().getTime());
        databinding.ncPlace.setText(shareViewModel.getNewConversation().getPlace());
        databinding.ncIsImportant.setIsRightClicked(!shareViewModel.getNewConversation().isImportant());
        setPhotoToImageView(FileManager.getBitmapPhoto(shareViewModel.getNewConversation().getPhoto()));
    }

    private void setPhotoToImageView(Bitmap bitmap) {
        if (bitmap != null) {
            databinding.ncImage.setImageBitmap(bitmap);
//                    userImage.setImageBitmap(getResizedBitmap(userImage, bitmap));
            ImageHelper.resizeImage(databinding.ncImage, bitmap);
            if (shareViewModel.getNewConversation() == null) {
                databinding.ncImage.setRotation(0);
                databinding.ncRotateRounder.setVisibility(View.INVISIBLE);
            } else {
                databinding.ncImage.setRotation(shareViewModel.getNewConversation().getAngle());
                databinding.ncRotateRounder.setVisibility(View.VISIBLE);
            }
        }

    }

    private void initContactsRecyclerView(List<Contact> contacts) {
        RVITAdapter.RVITAdapterViewHolder.CustomItemClick contactListener = (name, photo) -> {
            //Set gender to shareviewModel.contact
            Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
            databinding.ncContacts.setText(name);
            databinding.ncContacts.setIcon(photo);
            databinding.ncContacts.collapse(true);
        };

        genderAdapter = new RVITAdapter(createContactList(contacts), contactListener, databinding.ncContacts.getExpansionLayout());
        databinding.ncContacts.setAdapter(genderAdapter);
        databinding.ncContacts.setHasFixedSize(false);
    }

    private List<ImageAndText> createContactList(List<Contact> contacts) {
        ArrayList<ImageAndText> resultList = new ArrayList<>();
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() != shareViewModel.getThisContact().getValue().getId())
                resultList.add(new ImageAndText(FileManager.getBitmapPhoto(contacts.get(i).getPhoto()),
                        contacts.get(i).getFullName()));
        }
        return resultList;
    }

    private void initObservers() {
        shareViewModel.getNewConversationBitmap().observe(getActivity(), this::setPhotoToImageView);
        shareViewModel.getContactsList().observe(getActivity(), this::initContactsRecyclerView);
    }

    private void setListeners() {
        databinding.ncButton.setOnClickListener(v -> {
            if (isEdit) {
                getAllDataFromFields();
                shareViewModel.updateConversation(getContext());
            } else {
                shareViewModel.getNewConversation().addContact(shareViewModel.getThisContact().getValue().getId());
                shareViewModel.getNewConversation().setChat(databinding.ncConversation.getText().toString());
                shareViewModel.addNewConversation(getContext(), shareViewModel.getNewConversation());
            }

            shareViewModel.setNewConversation(null);
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
        databinding.ncRotateRounder.setOnClickListener(v -> {
            float newAngle = 0;
            if(shareViewModel.getNewConversation().getAngle() < 270) {
                newAngle = shareViewModel.getNewConversation().getAngle() + 90;
            }
            databinding.ncImage.setRotation(newAngle);
            shareViewModel.getNewConversation().setAngle(newAngle);
        });
    }

    private void getAllDataFromFields() {
        shareViewModel.getNewConversation().setChat(databinding.ncConversation.getText().toString());
        shareViewModel.getNewConversation().setTime(databinding.ncTime.getText());
        shareViewModel.getNewConversation().setDate(databinding.ncDate.getText());
        shareViewModel.getNewConversation().setPlace(databinding.ncPlace.getText());
//        shareViewModel.getNewConversation().setPhoto(); // es fa a l'activity
        //TODO: fer botó de rotar
//        shareViewModel.getNewConversation().setAngle();
//        shareViewModel.getNewConversation().setContacts();
        shareViewModel.getNewConversation().setImportant(!databinding.ncIsImportant.getIsRightClicked());
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
            shareViewModel.getNewConversation().setTime(time);
            databinding.ncTime.setText(time);
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
