package com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.nunnos.keepintouch.presentation.feature.main.activity.MainActivity;
import com.nunnos.keepintouch.presentation.feature.main.activity.vm.MainViewModel;
import com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.dialogs.BackgroundColorPickerFragment;
import com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.dialogs.DatePickerFragment;
import com.nunnos.keepintouch.utils.Constants;
import com.nunnos.keepintouch.utils.ImageHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewContactFragment extends BaseFragmentViewModelLiveData<MainViewModel, FragmentMainNewContactBinding> {

    public static final String TAG = "NewContactFragment";

    private RVITAdapter genderAdapter;
    private RVITAdapter sexualOrientationAdapter;
    private RVContactAdapter contactsAdapter;


    public NewContactFragment() {
        //Required empty public constructor
    }

    public static NewContactFragment newInstance() {
        NewContactFragment fragment = new NewContactFragment();
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
    }

    private void initObservers() {
        shareViewModel.getNewContactBitmap().observe(getActivity(), this::setPhotoToImageView);
        shareViewModel.getContacts().observe(getActivity(), this::initRelativesRecyclerView);
    }

    private void setPhotoToImageView(Bitmap bitmap) {
        if (bitmap == null) return;
        shareViewModel.getNewContact().setAngle(0);
        databinding.newContactImage.setImageBitmap(bitmap);
        ImageHelper.resizeImage(databinding.newContactImage, bitmap);
        databinding.newContactRotateRounder.setVisibility(View.VISIBLE);
        databinding.newContactDeleteImageRounder.setVisibility(View.VISIBLE);
    }

    private void initRelativesRecyclerView(List<Contact> relatives) {
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
    }

    private void initView() {
        initRecyclerViews();
        shareViewModel.getNewContact().setBgColor(Constants.DEFAULT_CARD_BG_COLOR);
        databinding.newContactBackgroundColor.setColorId(Constants.DEFAULT_CARD_BG_COLOR);
        databinding.newContactEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        databinding.newContactTelephone.setInputType(InputType.TYPE_CLASS_NUMBER);
        databinding.newContactHowWeMet.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    private void setView() {
        if (shareViewModel.getNewContact() == null || shareViewModel.getNewContact().isEmpty())
            return;
        Contact newContact = shareViewModel.getNewContact();
        databinding.newContactName.setText(newContact.getName());
        databinding.newContactSurname1.setText(newContact.getSurname1());
        databinding.newContactSurname2.setText(newContact.getSurname2());
        databinding.newContactGenderExpanable.setText(newContact.getGender());
        databinding.newContactSexualOrientationExpanable.setText(newContact.getSexualOrientation());
        databinding.newContactBirthday.setText(newContact.getBirthday());
        databinding.newContactAdress.setText(newContact.getAddress());
        databinding.newContactProfession.setText(newContact.getProfession());
        databinding.newContactPlaceofwork.setText(newContact.getPlaceOfWork());
        databinding.newContactHowWeMet.setText(newContact.getHowWeMet());
        databinding.newContactLanguage.setText(newContact.getLanguage());
        databinding.newContactReligion.setText(newContact.getReligion());
        databinding.newContactRelatives.setText(newContact.getRelatives());
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
        databinding.newContactSaveButton.setOnClickListener(__ -> saveContact());
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
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).launchSlidingUpActivityForResult(
                        ((MainActivity) getActivity()).getResultLauncher(),
                        photoPickerIntent);
            }
        });
        databinding.newContactRotateRounder.setOnClickListener(__ -> {
            float newAngle = shareViewModel.getNewContact().getAngle() + 90;
            databinding.newContactImage.setRotation(newAngle);
            shareViewModel.getNewContact().setAngle(newAngle);
        });

        databinding.newContactDeleteImageRounder.setOnClickListener(__ -> {
            shareViewModel.getNewContact().setPhoto("");
            databinding.newContactImage.setRotation(0);
            setPhotoToImageView(ImageHelper.drawableToBitmap(getActivity().getDrawable(R.drawable.ic_person_full)));
            shareViewModel.getNewContact().setAngle(0);
            databinding.newContactRotateRounder.setVisibility(View.INVISIBLE);
            databinding.newContactDeleteImageRounder.setVisibility(View.INVISIBLE);
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            CustomDate date = new CustomDate(day, month, year);
            final String selectedDate = date.toString();
            Contact newContact = shareViewModel.getNewContact();
            newContact.setBirthday(selectedDate);
            shareViewModel.setNewContact(newContact);
            databinding.newContactBirthday.setText(selectedDate);
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private void showBgColorickerDialog() {
        BackgroundColorPickerFragment newFragment = BackgroundColorPickerFragment.newInstance((bgSelected) -> {
            Contact newContact = shareViewModel.getNewContact();
            newContact.setBgColor(bgSelected);
            shareViewModel.setNewContact(newContact);
            databinding.newContactBackgroundColor.setColorId(bgSelected);
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private void saveContact() {
        if (checkAllRight()) {
            shareViewModel.setLastIndex(shareViewModel.getLastIndex() + 1);
            Contact contact = new Contact(databinding.newContactName.getText(),
                    databinding.newContactSurname1.getText(),
                    databinding.newContactSurname2.getText(),
                    databinding.newContactGenderExpanable.getText(),
                    databinding.newContactSexualOrientationExpanable.getText(),
                    databinding.newContactBirthdaySwitch.getIsRightClicked() ? databinding.newContactAge.getText() : databinding.newContactBirthday.getText(),
                    databinding.newContactBirthdaySwitch.getIsRightClicked(),
                    databinding.newContactAdress.getText(),
                    databinding.newContactProfession.getText(),
                    databinding.newContactPlaceofwork.getText(),
                    databinding.newContactHowWeMet.getText(),
                    databinding.newContactLanguage.getText(),
                    databinding.newContactReligion.getText(),
                    "",
                    "",
                    false,
                    shareViewModel.getNewContact().getBgColor(),
                    shareViewModel.getNewContact().getPhoto(),
                    shareViewModel.getNewContact().getAngle(),
                    shareViewModel.getLastIndex(),
                    databinding.newContactAlias.getText(),
                    databinding.newContactTelephone.getText(),
                    databinding.newContactEmail.getText(),
                    "",
                    -1,
                    "",
                    databinding.newContactSocialMedia.getText(),
                    "");

            contact.addRelativeList(databinding.newContactRelatives.getSelectedContacts());
            shareViewModel.saveContact(getContext(), contact);
            shareViewModel.setRefreshOnBack(true);
            shareViewModel.setNewContactBitmap(null);
            shareViewModel.navigateToMain();
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
        databinding.setShareviewmodelmain(shareViewModel);
    }

    @Override
    protected boolean isShareViewModel() {
        return true;
    }
}
