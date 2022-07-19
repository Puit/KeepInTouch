package com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.mukesh.countrypicker.Country;
import com.nunnos.keepintouch.BuildConfig;
import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.data.CustomDate;
import com.nunnos.keepintouch.databinding.FragmentMainNewContactBinding;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.presentation.component.CustomSwitch;
import com.nunnos.keepintouch.presentation.component.recyclerviewitemtext.ImageAndText;
import com.nunnos.keepintouch.presentation.component.recyclerviewitemtext.RVITAdapter;
import com.nunnos.keepintouch.presentation.feature.camera.CameraActivity;
import com.nunnos.keepintouch.presentation.feature.main.activity.vm.MainViewModel;
import com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.dialogs.BackgroundColorPickerFragment;
import com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.dialogs.DatePickerFragment;
import com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.vm.NewContactViewModel;
import com.nunnos.keepintouch.utils.CameraPreview;
import com.nunnos.keepintouch.utils.CameraUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class NewContactFragment extends BaseFragmentViewModelLiveData<NewContactViewModel, MainViewModel, FragmentMainNewContactBinding> {

    private static final String TAG = "NewContactFragment";

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 2;
    static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 3;
    static final int REQUEST_IMAGE_GALLERY = 4;

    private RVITAdapter genderAdapter;
    private RVITAdapter sexualOrientationAdapter;
    private static String BACKGROUND_PICKER_TAG = "BACKGROUND_PICKER_TAG";

    private Camera mCamera;
    private CameraPreview mPreview;

    public NewContactFragment() {
        //Required empty public constructor
    }

    public static NewContactFragment newInstance() {
        NewContactFragment fragment = new NewContactFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

/*    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGenderRecyclerView();
    }*/

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
    }

    private void setPhotoToImageView(Bitmap bitmap) {
        databinding.newContactImage.setImageBitmap(bitmap);
    }

    private void initView() {
        initGenderRecyclerView();
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
        // databinding.newContactBackgroundColor.setText("newContact.getBgColor()");

    }

    private void initGenderRecyclerView() {
        RVITAdapter.RVITAdapterViewHolder.CustomItemClick genderListener = (gender, icon) -> {
            //Set gender to shareviewModel.contact
            Toast.makeText(getContext(), gender, Toast.LENGTH_SHORT).show();
            databinding.newContactGenderExpanable.setText(gender);
            databinding.newContactGenderExpanable.setIcon(icon);
            databinding.newContactGenderExpanable.collapse(true);
        };

        genderAdapter = new RVITAdapter(createGendersList(), genderListener, databinding.newContactGenderExpanable.getExpansionLayout());
        databinding.newContactGenderExpanable.setAdapter(genderAdapter);
        databinding.newContactGenderExpanable.setHasFixedSize(false);

        RVITAdapter.RVITAdapterViewHolder.CustomItemClick sexualOrientationListener = (gender, icon) -> {
            //Set gender to shareviewModel.contact
            Toast.makeText(getContext(), gender, Toast.LENGTH_SHORT).show();
            databinding.newContactSexualOrientationExpanable.setText(gender);
            databinding.newContactSexualOrientationExpanable.setIcon(icon);
            databinding.newContactSexualOrientationExpanable.collapse(true);
        };

        sexualOrientationAdapter = new RVITAdapter(createSexualOrientationList(), sexualOrientationListener, databinding.newContactGenderExpanable.getExpansionLayout());
        databinding.newContactSexualOrientationExpanable.setAdapter(sexualOrientationAdapter);
        databinding.newContactSexualOrientationExpanable.setHasFixedSize(false);
    }

    private void initListeners() {
        databinding.newContactButton.setOnClickListener(v -> {
            saveContact();
        });
        databinding.newContactName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (TextUtils.isEmpty(databinding.newContactName.getText())) {
                    databinding.newContactName.setErrorState();
                } else {
                    databinding.newContactName.setSuccessState();
                }
            }
        });
        databinding.newContactName.onFocusChanged((v, hasFocus) -> {
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
        //     databinding.newContactAddImageButton.setOnClickListener(v -> CameraUtils.dispatchTakePictureIntent(getActivity()));
        databinding.newContactAddImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*//TODO: Mostrar banner de que se va a utilizar la camara?
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile(getActivity());
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getContext(), // PETA AQUI
                                BuildConfig.APPLICATION_ID + ".provider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        getActivity().startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }*/
                //CameraUtils.openCameraForResult(getActivity(), REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
                Intent intent=new Intent(getActivity(), CameraActivity.class);
                startActivityForResult(intent, 2);// Activity is started with requestCode 2
                //CameraUtils.showGallery(getActivity(),REQUEST_PERMISSION_READ_EXTERNAL_STORAGE, REQUEST_IMAGE_GALLERY);
            }
        });

        //TODO: HACER ALGO CON ESTO?
        Country.getAllCountries("es");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CameraUtils.openCameraForResult(getActivity(), REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
                }

                return;
            }
        }
    }

    private File createImageFile(Activity activity) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        String currentPhotoPath = image.getAbsolutePath();
        return image;
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
            Contact contact = new Contact(databinding.newContactName.getText(),
                    databinding.newContactSurname1.getText(),
                    databinding.newContactSurname2.getText(),
                    databinding.newContactGenderExpanable.getText(),
                    databinding.newContactSexualOrientationExpanable.getText(),
                    databinding.newContactBirthday.getText(),
                    databinding.newContactBirthdaySwitch.getIsRightClicked(),
                    databinding.newContactAdress.getText(),
                    databinding.newContactProfession.getText(),
                    databinding.newContactPlaceofwork.getText(),
                    databinding.newContactHowWeMet.getText(),
                    databinding.newContactLanguage.getText(),
                    databinding.newContactReligion.getText(),
                    databinding.newContactRelatives.getText(),
                    "",
                    false,
                    ContextCompat.getColor(getContext(), R.color.purple_500),
                    "picture.getPath()");
            shareViewModel.saveContact(getContext(), contact);
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
        databinding.setShareviewmodel(shareViewModel);
        databinding.setViewmodel(viewModel);
    }

    @Override
    protected boolean isShareViewModel() {
        return true;
    }
}
