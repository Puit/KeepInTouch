package com.nunnos.keepintouch.presentation.feature.contactinfo.activity;

import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.CONTACT_INFO;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.CONTACT_PERSONAL_DATA;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.CONVERSATIONS;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.EDIT_CONTACT;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.NEW_COMMENT;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.NEW_CONVERSATION;
import static com.nunnos.keepintouch.utils.Constants.EXTRA_CONTACT_SELECTED_ID;
import static com.nunnos.keepintouch.utils.Constants.EXTRA_UPDATE_MAIN;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseActivityViewModelLiveData;
import com.nunnos.keepintouch.databinding.ActivityContactInfoBinding;
import com.nunnos.keepintouch.presentation.component.BottomMenu;
import com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigationManager;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.utils.FileManager;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ContactInfoActivity extends BaseActivityViewModelLiveData<ContactInfoViewModel, ActivityContactInfoBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.retrieveLastIndex(this);
        getContact();
        setView();
        initObservers();
        redirectTo();
    }

    private void getContact() {
        int id = (int) getIntent().getExtras().get(EXTRA_CONTACT_SELECTED_ID);
        getShareViewModel().retrieveContact(this, id);
    }

    private void setView() {
        dataBinding.contactInfoActivityBottomMenu.setListener(new BottomMenu.ButtonsListeners() {
            @Override
            public void onButton1Click() {
                //Do nothing
            }

            @Override
            public void onButton2Click() {
                viewModel.navigateToConversation();
            }

            @Override
            public void onButton3Click() {
                viewModel.navigateToContactInfo();
            }

            @Override
            public void onButton4Click() {
                viewModel.navigateToContactPersonalData();
            }
        });
    }

    private void initObservers() {
        viewModel.getNavigation().observe(this, this::onNavigate);
    }

    private void onNavigate(Integer navigation) {
        ContactInfoNavigationManager.goTo(this, navigation, viewModel);
        switch (navigation) {
            case CONVERSATIONS:
                dataBinding.contactInfoActivityBottomMenu.setVisibility(View.VISIBLE);
                break;
            case CONTACT_INFO:
                dataBinding.contactInfoActivityBottomMenu.setVisibility(View.VISIBLE);
                break;
            case CONTACT_PERSONAL_DATA:
                dataBinding.contactInfoActivityBottomMenu.setVisibility(View.VISIBLE);
                break;
            case NEW_CONVERSATION:
            case EDIT_CONTACT:
            case NEW_COMMENT:
                dataBinding.contactInfoActivityBottomMenu.setVisibility(View.GONE);
                break;
            default:
                dataBinding.contactInfoActivityBottomMenu.setVisibility(View.VISIBLE);
        }
    }

    private void redirectTo() {
        viewModel.navigateToContactInfo();
        dataBinding.contactInfoActivityBottomMenu.setItemSelected(3); // Marca el botón de chat
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //Data es null porque la imagen se guarda en storage
            try {
                final Uri imageUri = data.getData();
                if (getShareViewModel().getNewConversation() == null) return;
                getShareViewModel().getNewConversation().setPhoto(FileManager.getPath(this, imageUri));
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                getShareViewModel().setNewConversationBitmap(BitmapFactory.decodeStream(imageStream));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    /*******************************************
     * IMPLEMENTATION BASE CLASS
     * *****************************************/
    @Override
    protected int layout() {
        return R.layout.activity_contact_info;
    }

    @Override
    protected void dataBindingViewModel() {
        viewModel = new ContactInfoViewModel();
        dataBinding.setShareviewmodel(viewModel);
    }

    @Override
    public void onBackPressed() {
        switch (viewModel.getNavigation().getValue()) {
            case NEW_CONVERSATION:
                super.onBackPressed();
                viewModel.resetNewConversation();
                viewModel.getNavigation().setValue(CONVERSATIONS);
                break;
            case EDIT_CONTACT:
                viewModel.getNavigation().setValue(CONTACT_PERSONAL_DATA);
                break;
            case NEW_COMMENT:
                viewModel.getNavigation().setValue(CONTACT_INFO);
                break;
            case CONVERSATIONS:
            case CONTACT_INFO:
            case CONTACT_PERSONAL_DATA:
            default:
                if (viewModel.isUpdateOnBack()) {
                    Intent replyIntent = new Intent();
                    replyIntent.putExtra(EXTRA_UPDATE_MAIN, true);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
        }
    }

    @Override
    protected Class<ContactInfoViewModel> getClassViewModel() {
        return ContactInfoViewModel.class;
    }
}
