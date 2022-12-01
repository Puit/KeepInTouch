package com.nunnos.keepintouch.presentation.feature.main.activity;

import static com.nunnos.keepintouch.utils.Constants.KEY_DELETE_CONTACT;
import static com.nunnos.keepintouch.utils.Constants.KEY_REFRESH_MAIN;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseActivityViewModelLiveData;
import com.nunnos.keepintouch.databinding.ActivityMainBinding;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.presentation.feature.main.MainNavigationManager;
import com.nunnos.keepintouch.presentation.feature.main.activity.vm.MainViewModel;
import com.nunnos.keepintouch.presentation.feature.main.fragment.main.MainFragment;
import com.nunnos.keepintouch.presentation.feature.main.fragment.search.SearchContactFragment;
import com.nunnos.keepintouch.utils.FileManager;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends BaseActivityViewModelLiveData<MainViewModel, ActivityMainBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initObservers();
        myOncreate();
        configureResultListener();
    }

    private void configureResultListener() {
        setOnActivityResultListener(new OnActivityResult() {
            @Override
            public void onResultOK(Intent intent) {
                if (intent.getStringExtra(WHAT_IM_CAPTURING) != null) {
                    switch (intent.getStringExtra(WHAT_IM_CAPTURING)) {
                        case KEY_REFRESH_MAIN:
                        case KEY_DELETE_CONTACT:
                            myOncreate();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + intent.getStringExtra(WHAT_IM_CAPTURING));
                    }
                }
            }

            @Override
            public void onResultKO(Intent intent) {
                //DO NOTHING
                if (intent.getType() != null) {
                    //Venimos de haber elegido la imagen en NewContactFragment
                    if (intent.getType().contains("image/")) {
                        try {
                            final Uri imageUri = intent.getData();
                            viewModel.getNewContact().setPhoto(FileManager.getPath(MainActivity.this, imageUri));
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            viewModel.setNewContactBitmap(BitmapFactory.decodeStream(imageStream));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    private void myOncreate() {
        askForExternalStoragePermissions();
        redirectTo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (viewModel.isRefreshOnBack()) {
            myOncreate();
        }
    }

    private void initObservers() {
        viewModel.getNavigation().observe(this, navigation -> {
            MainNavigationManager.goTo(this, navigation, viewModel);
        });
    }

    private Context getFragmentContext() {
        return dataBinding.activityFragmentContainer.getContext();
    }

    public void setBackgroundColorToNewContact(int backgroundColor) {
        Contact contact = viewModel.getNewContact();
        contact.setBgColor(backgroundColor);
        viewModel.setNewContact(contact);
    }

    private void redirectTo() {
        viewModel.navigateToMain();
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_NAVIGATE_TO_CONTACT_INFO) {
                if (data.getBooleanExtra(EXTRA_UPDATE_MAIN, false)) {
                    myOncreate();
                }
            } else {
                //Data es null porque la imagen se guarda en storage
                try {
                    final Uri imageUri = data.getData();
                    getShareViewModel().getNewContact().setPhoto(FileManager.getPath(this, imageUri));
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    getShareViewModel().setNewContactBitmap(BitmapFactory.decodeStream(imageStream));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            myOncreate();
        }
    }*/

    /**
     * permisssions
     */
    // Register the permissions callback, which handles the user's response to the
// system permissions dialog. Save the return value, an instance of
// ActivityResultLauncher, as an instance variable.
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });

    private void askForExternalStoragePermissions() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            Toast.makeText(this, "Permissions granted", Toast.LENGTH_LONG).show();

        } /*else if (shouldShowRequestPermissionRationale(...)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
            *//*showInContextUI(...);*//*
        } */ else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    /*******************************************
     * IMPLEMENTATION BASE CLASS
     * *****************************************/
    @Override
    protected int layout() {
        return R.layout.activity_main;
    }

    @Override
    protected void dataBindingViewModel() {
        viewModel = new MainViewModel();
        dataBinding.setShareviewmodel(viewModel);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_fragment_container);
        if (fragment instanceof MainFragment) {
            this.finishAndRemoveTask(); //TODO: matarà las notificaciones?
        } else if (fragment instanceof SearchContactFragment) {
            viewModel.clearSearch();
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected Class<MainViewModel> getClassViewModel() {
        return MainViewModel.class;
    }
}