package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.conversation;

import static com.nunnos.keepintouch.utils.Constants.COVERSATION_MAX_CHARS;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.data.CustomDate;
import com.nunnos.keepintouch.databinding.FragmentNewConversationBinding;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.presentation.component.CustomSwitch;
import com.nunnos.keepintouch.presentation.component.recyclerviews.contactsselector.RVContactAdapter;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.ContactInfoActivity;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.dialogs.DatePickerFragment;
import com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.dialogs.TimePickerFragment;
import com.nunnos.keepintouch.utils.AlertsManager;
import com.nunnos.keepintouch.utils.FileManager;
import com.nunnos.keepintouch.utils.ImageHelper;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.M)
public class NewConversationFragment extends BaseFragmentViewModelLiveData<ContactInfoViewModel, FragmentNewConversationBinding> {
    private RVContactAdapter contactsAdapter;
    private boolean isEdit = false;
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkLengthAndSetStyle(s);
        }

        public void afterTextChanged(Editable s) {
        }
    };

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
        shareViewModel.retrieveContacts(getContext());
    }

    private void setView() {
        if (!shareViewModel.getNewConversation().isEmpty()) {
            setViewForEdit();
        } else {
            databinding.ncIsImportant.setIsRightClicked(true);
        }
        checkLengthAndSetStyle(databinding.ncConversation.getText());
    }

    private void checkLengthAndSetStyle(CharSequence s) {
        if (s.length() <= COVERSATION_MAX_CHARS) {
            databinding.ncCounter.setTextColor(getContext().getColor(R.color.text_gray));
        } else {
            databinding.ncCounter.setTextColor(getContext().getColor(R.color.background_red));
        }
        //This sets a textview to the current length
        databinding.ncCounter.setText(s.length() + "/" + COVERSATION_MAX_CHARS);
    }

    private void setViewForEdit() {
        isEdit = true;

        databinding.ncConversation.setText(shareViewModel.getNewConversation().getChat());
        databinding.ncDate.setText(shareViewModel.getNewConversation().getDate());
        databinding.ncTime.setText(shareViewModel.getNewConversation().getTime());
        databinding.ncPlace.setText(shareViewModel.getNewConversation().getPlace());
        databinding.ncIsImportant.setIsRightClicked(!shareViewModel.getNewConversation().isImportant());
        setPhotoToImageView(FileManager.getBitmapPhoto(shareViewModel.getNewConversation().getPhoto()));
        databinding.ncDeleteButton.setVisibility(View.VISIBLE);
    }

    private void setPhotoToImageView(Bitmap bitmap) {
        if (bitmap != null) {
            databinding.ncImage.setImageBitmap(bitmap);
            ImageHelper.resizeImage(databinding.ncImage, bitmap);
            if (shareViewModel.getNewConversation() == null) {
                databinding.ncImage.setRotation(0);
                databinding.ncRotateRounder.setVisibility(View.INVISIBLE);
                databinding.ncDeleteImageRounder.setVisibility(View.INVISIBLE);
            } else {
                databinding.ncImage.setRotation(shareViewModel.getNewConversation().getAngle());
                databinding.ncRotateRounder.setVisibility(View.VISIBLE);
                databinding.ncDeleteImageRounder.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initContactsRecyclerView(List<Contact> contacts) {
        //Entra dos cops i dona problemes, aixi que ho netegem
        databinding.ncContacts.clearContacts();
        RVContactAdapter.RVContactdapterViewHolder.CustomItemClick contactListener = new RVContactAdapter.RVContactdapterViewHolder.CustomItemClick() {
            @Override
            public void onItemClick(Contact contact) {
                databinding.ncContacts.addSelectedContact(contact);
                databinding.ncContacts.collapse(true);
            }

            @Override
            public void onRightImageClick(Contact contact) {
                //Do nothing
            }
        };
        contactsAdapter = new RVContactAdapter(contacts,
                contactListener,
                databinding.ncContacts.getExpansionLayoutForSelection(),
                false);
        databinding.ncContacts.setAdapter(contactsAdapter);
        databinding.ncContacts.setHasFixedSize(false);
        //Add selected contacts

        databinding.ncContacts.addSelectedContact(shareViewModel.getThisContact().getValue());
        databinding.ncContacts.addSelectedContacts(shareViewModel.getNewConversation().getContacts(), contacts);
        databinding.ncContacts.collapse(true);
    }

    private void initObservers() {
        shareViewModel.getNewConversationBitmap().observe(getActivity(), this::setPhotoToImageView);
        shareViewModel.getContactsList().observe(getActivity(), this::initContactsRecyclerView);
    }

    private void setListeners() {
        databinding.ncSaveButton.setOnClickListener(__ -> save());
        databinding.ncDeleteButton.setOnClickListener(__ -> delete());
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
        databinding.ncAddImageButton.setOnClickListener(__ -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            if (getActivity() instanceof ContactInfoActivity) {
                ((ContactInfoActivity) getActivity()).launchSlidingUpActivityForResult(
                        ((ContactInfoActivity) getActivity()).getResultLauncher(),
                        photoPickerIntent);
            }
        });
        databinding.ncRotateRounder.setOnClickListener(__ -> {
            float newAngle = 0;
            if (shareViewModel.getNewConversation().getAngle() < 270) {
                newAngle = shareViewModel.getNewConversation().getAngle() + 90;
            }
            databinding.ncImage.setRotation(newAngle);
            shareViewModel.getNewConversation().setAngle(newAngle);
        });
        databinding.ncDeleteImageRounder.setOnClickListener(__ -> {
            shareViewModel.getNewConversation().setPhoto("");
            databinding.ncImage.setRotation(0);
            databinding.ncImage.setImageDrawable(null);
            shareViewModel.getNewConversation().setAngle(0);
            databinding.ncRotateRounder.setVisibility(View.INVISIBLE);
            databinding.ncDeleteImageRounder.setVisibility(View.INVISIBLE);
        });
        databinding.ncConversation.addTextChangedListener(mTextEditorWatcher);
    }

    private void save() {
        if (databinding.ncConversation.getText().length() <= COVERSATION_MAX_CHARS) {
            if (databinding.ncContacts.getSelectedContacts().size() > 0) {
                getAllDataFromFields();
                if (isEdit) {
                    shareViewModel.updateConversation(getContext());
                } else {
                    shareViewModel.addNewConversation(getContext(), shareViewModel.getNewConversation());
                }

                shareViewModel.updateOnBack();
                shareViewModel.resetNewConversation();
                databinding.ncContacts.clearContacts();
                shareViewModel.navigateToConversation();
            } else {
                AlertsManager.OneButtonAlertListener listener = () -> {
                    //Do Nothing
                };
                AlertsManager.showOneButtonAlert(getActivity(),
                        listener,
                        getString(R.string.new_conversation_error_at_least_one_person_selected),
                        getString(R.string.ok),
                        true);
            }
        } else {
            AlertsManager.OneButtonAlertListener listener = () -> {
                //Do Nothing
            };
            AlertsManager.showOneButtonAlert(getActivity(),
                    listener,
                    getString(R.string.new_conversation_error_max_characters),
                    getString(R.string.ok),
                    true);
        }
    }

    private void delete() {
        AlertsManager.TwoButtonsAlertListener listener = new AlertsManager.TwoButtonsAlertListener() {
            @Override
            public void onLeftClick() {
                shareViewModel.deleteConversation(getContext(), shareViewModel.getNewConversation());

                shareViewModel.resetNewConversation();
                databinding.ncContacts.clearContacts();
                shareViewModel.navigateToConversation();
                //TODO: FORÇAR QUE REFRESQUI
            }

            @Override
            public void onRightClick() {
                //Do nothing
            }
        };
        AlertsManager.showTwoButtonsAlert(getActivity(), listener,
                getString(R.string.new_conversation_question_delete_message),
                getString(R.string.ok),
                getString(R.string.cancel),
                true);

    }

    private void getAllDataFromFields() {
        shareViewModel.getNewConversation().setChat(databinding.ncConversation.getText().toString());
        if (databinding.ncTime.getText().equals(getString(R.string.time_format))) {
            shareViewModel.getNewConversation().setTime("");
        } else {
            shareViewModel.getNewConversation().setTime(databinding.ncTime.getText());
        }
        if (databinding.ncDate.getText().equals(getString(R.string.date_format))) {
            shareViewModel.getNewConversation().setDate("");
        } else {
            shareViewModel.getNewConversation().setDate(databinding.ncDate.getText());
        }
        shareViewModel.getNewConversation().setPlace(databinding.ncPlace.getText());
        shareViewModel.getNewConversation().addContact(shareViewModel.getThisContact().getValue().getId());
        shareViewModel.getNewConversation().setImportant(!databinding.ncIsImportant.getIsRightClicked());
        shareViewModel.getNewConversation().addContactList(databinding.ncContacts.getSelectedContacts());
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
            String time = "";
            if (hour < 10) {
                time = "0";
            }
            time += hour + ":";
            if (minute < 10) {
                time += "0";
            }
            time += minute;
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
