package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.personaldata;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.data.CustomDate;
import com.nunnos.keepintouch.databinding.FragmentMainNewContactBinding;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.presentation.component.CustomSwitch;
import com.nunnos.keepintouch.presentation.component.recyclerviews.contactsselector.RVContactAdapter;
import com.nunnos.keepintouch.presentation.component.recyclerviews.itemtext.ImageAndText;
import com.nunnos.keepintouch.presentation.component.recyclerviews.itemtext.RVITAdapter;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.ContactInfoActivity;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.dialogs.BackgroundColorPickerFragment;
import com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.dialogs.DatePickerFragment;
import com.nunnos.keepintouch.utils.FileManager;
import com.nunnos.keepintouch.utils.ImageHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditContactFragment extends BaseFragmentViewModelLiveData<ContactInfoViewModel, FragmentMainNewContactBinding> {
    private static final String TAG = "EditContactFragment";

    private RVITAdapter genderAdapter;
    private RVITAdapter sexualOrientationAdapter;
    private RVContactAdapter contactsAdapter;


    public EditContactFragment() {
        //Required empty public constructor
    }

    public static EditContactFragment newInstance() {
        EditContactFragment fragment = new EditContactFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initObservers();
        setView();
        initListeners();
        shareViewModel.retrieveContacts(getContext());
    }

    private void initObservers() {
        shareViewModel.getThisContactBitmap().observe(getActivity(), this::setPhotoToImageView);
        shareViewModel.getContactsList().observe(getActivity(), this::initContactsRecyclerView);
    }

    private void setPhotoToImageView(Bitmap bitmap) {
        setPhotoToImageView(bitmap, 0);
    }

    private void setPhotoToImageView(Bitmap bitmap, float angle) {
        if (bitmap == null) return;
        shareViewModel.getThisContact().getValue().setAngle(angle);
        databinding.newContactImage.setImageBitmap(bitmap);
        ImageHelper.resizeImage(databinding.newContactImage, bitmap);
        if (bitmap != null) {
            databinding.newContactImage.setImageBitmap(bitmap);
            ImageHelper.resizeImage(databinding.newContactImage, bitmap);
            if (shareViewModel.getThisContact().getValue() == null) {
                databinding.newContactImage.setRotation(0);
                databinding.newContactRotateRounder.setVisibility(View.INVISIBLE);
                databinding.newContactDeleteImageRounder.setVisibility(View.INVISIBLE);
            } else {
                databinding.newContactImage.setRotation(shareViewModel.getThisContact().getValue().getAngle());
                databinding.newContactRotateRounder.setVisibility(View.VISIBLE);
                databinding.newContactDeleteImageRounder.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initContactsRecyclerView(List<Contact> relatives) {
        //Entra dos cops i dona problemes, aixi que ho netegem
        databinding.newContactRelatives.clearContacts();
        RVContactAdapter.RVContactdapterViewHolder.CustomItemClick contactListener = new RVContactAdapter.RVContactdapterViewHolder.CustomItemClick() {
            @Override
            public void onItemClick(Contact contact) {
                databinding.newContactRelatives.addSelectedContact(contact);
                databinding.newContactRelatives.collapse(true);
            }

            @Override
            public void onRightImageClick(Contact contact) {
                //Do nothing
            }
        };
        contactsAdapter = new RVContactAdapter(relatives,
                contactListener,
                databinding.newContactRelatives.getExpansionLayoutForSelection(),
                false);
        databinding.newContactRelatives.setAdapter(contactsAdapter);
        databinding.newContactRelatives.setHasFixedSize(false);
        //Add selected contacts

        databinding.newContactRelatives.addSelectedContact(shareViewModel.getThisContact().getValue());
        databinding.newContactRelatives.addSelectedContacts(shareViewModel.getNewConversation().getContacts(), relatives);
        databinding.newContactRelatives.collapse(true);
    }

    private void initView() {
        initRecyclerViews();
        databinding.newContactDeleteButton.setVisibility(View.VISIBLE);
        databinding.newContactEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        databinding.newContactTelephone.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    private void setView() {
        if (shareViewModel.getThisContact() == null || shareViewModel.getThisContact().getValue().isEmpty())
            return;
        Contact newContact = shareViewModel.getThisContact().getValue();
        databinding.newContactName.setText(newContact.getName());
        databinding.newContactSurname1.setText(newContact.getSurname1());
        databinding.newContactSurname2.setText(newContact.getSurname2());
        databinding.newContactAlias.setText(newContact.getAlias());
        databinding.newContactGenderExpanable.setText(newContact.getGender());
        databinding.newContactSexualOrientationExpanable.setText(newContact.getSexualOrientation());
        databinding.newContactBirthdaySwitch.setIsRightClicked(!newContact.isRealBirthday());
        if (newContact.isRealBirthday()) {
            databinding.newContactBirthday.setText(newContact.getBirthday());
            databinding.newContactBirthday.setVisibility(View.VISIBLE);
            databinding.newContactAge.setVisibility(View.GONE);
        } else {
            databinding.newContactAge.setText(newContact.getBirthday());
            databinding.newContactBirthday.setVisibility(View.GONE);
            databinding.newContactAge.setVisibility(View.VISIBLE);
        }
        databinding.newContactAdress.setText(newContact.getAddress());
        databinding.newContactProfession.setText(newContact.getProfession());
        databinding.newContactPlaceofwork.setText(newContact.getPlaceOfWork());
        databinding.newContactHowWeMet.setText(newContact.getHowWeMet());
        databinding.newContactLanguage.setText(newContact.getLanguage());
        databinding.newContactReligion.setText(newContact.getReligion());
        setPhotoToImageView(FileManager.getBitmapPhoto(newContact.getPhoto()), newContact.getAngle());
        databinding.newContactImage.setRotation(newContact.getAngle());
        databinding.newContactTelephone.setText(newContact.getTelephone());
        databinding.newContactEmail.setText(newContact.getEmail());
        databinding.newContactSocialMedia.setText(newContact.getSocialMedia());
        databinding.newContactBackgroundColor.setColorId(newContact.getBgColor());
    }

    private void initRecyclerViews() {
        RVITAdapter.RVITAdapterViewHolder.CustomItemClick genderListener = (gender, icon) -> {
            //Set gender to shareviewModel.contact
            databinding.newContactGenderExpanable.setText(gender);
            databinding.newContactGenderExpanable.setIcon(icon);
            databinding.newContactGenderExpanable.collapse(true);
        };

        genderAdapter = new RVITAdapter(createGendersList(), genderListener, databinding.newContactGenderExpanable.getExpansionLayout());
        databinding.newContactGenderExpanable.setAdapter(genderAdapter);
        databinding.newContactGenderExpanable.setHasFixedSize(false);

        RVITAdapter.RVITAdapterViewHolder.CustomItemClick sexualOrientationListener = (gender, icon) -> {
            //Set gender to shareviewModel.contact
            databinding.newContactSexualOrientationExpanable.setText(gender);
            databinding.newContactSexualOrientationExpanable.setIcon(icon);
            databinding.newContactSexualOrientationExpanable.collapse(true);
        };

        sexualOrientationAdapter = new RVITAdapter(createSexualOrientationList(), sexualOrientationListener, databinding.newContactGenderExpanable.getExpansionLayout());
        databinding.newContactSexualOrientationExpanable.setAdapter(sexualOrientationAdapter);
        databinding.newContactSexualOrientationExpanable.setHasFixedSize(false);
    }

    private void initListeners() {
        databinding.newContactSaveButton.setOnClickListener(__ -> updateContact());
        databinding.newContactName.setOnFocusChangeListener((__, hasFocus) -> {
            if (!hasFocus) {
                if (TextUtils.isEmpty(databinding.newContactName.getText())) {
                    databinding.newContactName.setErrorState();
                } else {
                    databinding.newContactName.setSuccessState();
                }
            }
        });
        databinding.newContactName.onFocusChanged((__, hasFocus) -> {
            if (!hasFocus) {
                if (TextUtils.isEmpty(databinding.newContactName.getText())) {
                    databinding.newContactName.setErrorState();
                } else {
                    databinding.newContactName.setSuccessState();
                }
            }
        });
        databinding.newContactBirthday.setListener(this::showDatePickerDialog);
        databinding.newContactBirthdaySwitch.setListener(new CustomSwitch.CustomListener() {
            @Override
            public void onLeft() { //Birthday
                databinding.newContactBirthday.setVisibility(View.VISIBLE);
                databinding.newContactAge.setVisibility(View.GONE);
            }

            @Override
            public void onRight() { //AGE
                databinding.newContactAge.setVisibility(View.VISIBLE);
                databinding.newContactBirthday.setVisibility(View.GONE);
            }
        });

        databinding.newContactBackgroundColor.setListener(this::showBgColorickerDialog);
        databinding.newContactAddImageButton.setOnClickListener(__ -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            if (getActivity() instanceof ContactInfoActivity) {
                ((ContactInfoActivity) getActivity()).launchSlidingUpActivityForResult(
                        ((ContactInfoActivity) getActivity()).getResultLauncher(),
                        photoPickerIntent);
            }
        });
        databinding.newContactRotateRounder.setOnClickListener(__ -> {
            float newAngle = shareViewModel.getThisContact().getValue().getAngle() + 90;
            databinding.newContactImage.setRotation(newAngle);
            shareViewModel.getThisContact().getValue().setAngle(newAngle);
        });
        databinding.newContactDeleteImageRounder.setOnClickListener(__ -> {
            shareViewModel.getThisContact().getValue().setPhoto("");
            databinding.newContactImage.setRotation(0);
            databinding.newContactImage.setImageDrawable(getActivity().getDrawable(R.drawable.ic_person_full));
            shareViewModel.getThisContact().getValue().setAngle(0);
            databinding.newContactRotateRounder.setVisibility(View.INVISIBLE);
            databinding.newContactDeleteImageRounder.setVisibility(View.INVISIBLE);
        });
        databinding.newContactDeleteButton.setOnClickListener(__ -> deleteContact());
    }

    private void deleteContact() {
        databinding.newContactRelatives.clearContacts();
        shareViewModel.deleteContact(getActivity(), shareViewModel.getThisContact().getValue());
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            CustomDate date = new CustomDate(day, month, year);
            final String selectedDate = date.toString();
            Contact newContact = shareViewModel.getThisContact().getValue();
            newContact.setBirthday(selectedDate);
            shareViewModel.getThisContact().setValue(newContact);
            databinding.newContactBirthday.setText(selectedDate);
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private void showBgColorickerDialog() {
        BackgroundColorPickerFragment newFragment = BackgroundColorPickerFragment.newInstance((bgSelected) -> {
            Contact newContact = shareViewModel.getThisContact().getValue();
            newContact.setBgColor(bgSelected);
            shareViewModel.getThisContact().setValue(newContact);
            databinding.newContactBackgroundColor.setColorId(bgSelected);
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private void updateContact() {
        if (checkAllRight()) {
            shareViewModel.setLastIndex(shareViewModel.getLastIndex() + 1);
            Contact contact = new Contact(shareViewModel.getThisContact().getValue().getId(),
                    databinding.newContactName.getText(),
                    databinding.newContactSurname1.getText(),
                    databinding.newContactSurname2.getText(),
                    databinding.newContactGenderExpanable.getText(),
                    databinding.newContactSexualOrientationExpanable.getText(),
                    databinding.newContactBirthdaySwitch.getIsRightClicked() ? databinding.newContactAge.getText() : databinding.newContactBirthday.getText(),
                    !databinding.newContactBirthdaySwitch.getIsRightClicked(),
                    databinding.newContactAdress.getText(),
                    databinding.newContactProfession.getText(),
                    databinding.newContactPlaceofwork.getText(),
                    databinding.newContactHowWeMet.getText(),
                    databinding.newContactLanguage.getText(),
                    databinding.newContactReligion.getText(),
                    "",
                    shareViewModel.getThisContact().getValue().getConversations(),
                    shareViewModel.getThisContact().getValue().isFavorite(),
                    shareViewModel.getThisContact().getValue().getBgColor(),
                    shareViewModel.getThisContact().getValue().getPhoto(),
                    shareViewModel.getThisContact().getValue().getAngle(),
                    shareViewModel.getLastIndex(),
                    databinding.newContactAlias.getText(),
                    databinding.newContactTelephone.getText(),
                    databinding.newContactEmail.getText(),
                    shareViewModel.getThisContact().getValue().getComments(),
                    shareViewModel.getThisContact().getValue().getDaysToCall(),
                    shareViewModel.getThisContact().getValue().getDayOfDeath(),
                    databinding.newContactSocialMedia.getText(),
                    shareViewModel.getThisContact().getValue().getNotification());
            contact.addRelativeList(databinding.newContactRelatives.getSelectedContacts());
            shareViewModel.updateThisContact(getContext(), contact);
            shareViewModel.updateOnBack();
            shareViewModel.navigateToContactPersonalData();
        } else {
            Toast.makeText(getContext(), "Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkAllRight() {
        if (TextUtils.isEmpty(databinding.newContactName.getText())) {
            databinding.newContactName.setErrorState();
            return false;
        }
        databinding.newContactName.setSuccessState();
        return true;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private List<ImageAndText> createGendersList() {
        ArrayList<ImageAndText> resultList = new ArrayList<>();
        List<String> gendersList = Arrays.asList(getResources().getStringArray(R.array.genders));
        for (int i = 0; i < gendersList.size(); i++) {
            switch (i) {
                case 0:
                    resultList.add(new ImageAndText(getContext().getDrawable(R.drawable.ic_male), gendersList.get(i)));
                    break;
                case 1:
                    resultList.add(new ImageAndText(getContext().getDrawable(R.drawable.ic_female), gendersList.get(i)));
                    break;
                case 2:
                    resultList.add(new ImageAndText(getContext().getDrawable(R.drawable.ic_non_binary), gendersList.get(i)));
                    break;
                case 3:
                    resultList.add(new ImageAndText(getContext().getDrawable(R.drawable.ic_transgender), gendersList.get(i)));
                    break;
                case 4:
                    resultList.add(new ImageAndText(getContext().getDrawable(R.drawable.ic_unknown), gendersList.get(i)));
                    break;
            }
        }
        return resultList;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private List<ImageAndText> createSexualOrientationList() {
        ArrayList<ImageAndText> resultList = new ArrayList<>();
        List<String> sexualOrientationList = Arrays.asList(getResources().getStringArray(R.array.sexual_orientation));
        for (int i = 0; i < sexualOrientationList.size(); i++) {
            switch (i) {
                case 0:
                    resultList.add(new ImageAndText(getContext().getDrawable(R.drawable.ic_heterosexual), sexualOrientationList.get(i)));
                    break;
                case 1:
                    resultList.add(new ImageAndText(getContext().getDrawable(R.drawable.ic_gay), sexualOrientationList.get(i)));
                    break;
                case 2:
                    resultList.add(new ImageAndText(getContext().getDrawable(R.drawable.ic_lesbian), sexualOrientationList.get(i)));
                    break;
                case 3:
                    resultList.add(new ImageAndText(getContext().getDrawable(R.drawable.ic_bisexual_woman), sexualOrientationList.get(i)));
                    break;
                case 4:
                    resultList.add(new ImageAndText(getContext().getDrawable(R.drawable.ic_pansexual), sexualOrientationList.get(i)));
                    break;
                case 5:
                    resultList.add(new ImageAndText(getContext().getDrawable(R.drawable.ic_asexual), sexualOrientationList.get(i)));
                    break;
                case 6:
                    resultList.add(new ImageAndText(getContext().getDrawable(R.drawable.ic_unknown), sexualOrientationList.get(i)));
                    break;
            }
        }
        return resultList;
    }

    //Region Base Methods

    @Override
    protected int layout() {
        return R.layout.fragment_main_new_contact;
    }

    @Override
    protected void dataBindingViewModel() {
        databinding.setShareviewmodeleditcontact(shareViewModel);
    }

    @Override
    protected boolean isShareViewModel() {
        return true;
    }
}