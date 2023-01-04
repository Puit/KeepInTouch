package com.nunnos.keepintouch.presentation.feature.contactinfo.activity;

import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.CONTACT_INFO;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.CONTACT_PERSONAL_DATA;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.CONVERSATIONS;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.EDIT_CONTACT;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.NEW_COMMENT;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.NEW_CONVERSATION;
import static com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigation.NEW_NOTIFICATION;
import static com.nunnos.keepintouch.utils.Constants.EXTRA_CONTACT_SELECTED_ID;
import static com.nunnos.keepintouch.utils.Constants.KEY_REFRESH_MAIN;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseActivityViewModelLiveData;
import com.nunnos.keepintouch.databinding.ActivityContactInfoBinding;
import com.nunnos.keepintouch.presentation.component.BottomMenu;
import com.nunnos.keepintouch.presentation.feature.contactinfo.ContactInfoNavigationManager;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.conversation.NewConversationFragment;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.personaldata.EditContactFragment;
import com.nunnos.keepintouch.utils.FileManager;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ContactInfoActivity extends BaseActivityViewModelLiveData<ContactInfoViewModel, ActivityContactInfoBinding> {

    public final static String TAG = "ContactInfoActivity";
    public final static int FAVORITE_INDEX = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.retrieveLastIndex(this);
        getContact();
        setView();
        initObservers();
        redirectTo();
        configureResultListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notification_favorite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_notification:
                if (viewModel.getThisContact().getValue() == null) return true;
                if (viewModel.getThisContact().getValue().getNotification() == null||viewModel.getThisContact().getValue().getNotification().contains("a")) {
                    item.setIcon(R.drawable.ic_baseline_notifications_none_24);
                    viewModel.getThisContact().getValue().setFavorite(false);
                    viewModel.showNewNotification();
                } else {
                    item.setIcon(R.drawable.ic_baseline_notifications_24);
                    viewModel.getThisContact().getValue().setFavorite(true);
                    viewModel.showNewNotification();
                }
                viewModel.updateThisContact(this);
                viewModel.updateOnBack();

                break;
            case R.id.action_favorite:
                if (viewModel.getThisContact().getValue() == null) return true;
                if (viewModel.getThisContact().getValue().isFavorite()) {
                    item.setIcon(R.drawable.ic_baseline_favorite_border_24);
                    viewModel.getThisContact().getValue().setFavorite(false);
                } else {
                    item.setIcon(R.drawable.ic_baseline_favorite_24);
                    viewModel.getThisContact().getValue().setFavorite(true);
                }
                viewModel.updateThisContact(this);
                viewModel.updateOnBack();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.getItem(FAVORITE_INDEX);

        item.setIcon(viewModel.getThisContact().getValue().isFavorite() ?
                R.drawable.ic_baseline_favorite_24 :
                R.drawable.ic_baseline_favorite_border_24);
        return super.onPrepareOptionsMenu(menu);
    }

    private void configureResultListener() {
        setOnActivityResultListener(new OnActivityResult() {
            @Override
            public void onResultOK(Intent intent) {
                //DO NOTHING
            }

            @Override
            public void onResultKO(Intent intent) {
                try {
                    final Uri imageUri = intent.getData();
                    if (getSupportFragmentManager().getFragments().size() >= 1) {
                        Fragment currentFragment = getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1);
                        if (currentFragment instanceof EditContactFragment) {
                            viewModel.getThisContact().getValue().setPhoto(FileManager.getPath(ContactInfoActivity.this, imageUri));
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            viewModel.setThisContactBitmap(BitmapFactory.decodeStream(imageStream));
                        }
                        if (currentFragment instanceof NewConversationFragment) {
                            if (viewModel.getNewConversation() == null) return;
                            viewModel.getNewConversation().setPhoto(FileManager.getPath(ContactInfoActivity.this, imageUri));
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            viewModel.setNewConversationBitmap(BitmapFactory.decodeStream(imageStream));
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(ContactInfoActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
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
            case NEW_NOTIFICATION:
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
            case NEW_NOTIFICATION:
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
                    replyIntent.putExtra(WHAT_IM_CAPTURING, KEY_REFRESH_MAIN);
                    replyIntent.putExtra(RESULT, true);
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
