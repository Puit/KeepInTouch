package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.info;

import static com.nunnos.keepintouch.utils.Constants.COMMENT_MAX_CHARS;
import static com.nunnos.keepintouch.utils.Constants.COVERSATION_MAX_CHARS;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.data.CustomDate;
import com.nunnos.keepintouch.databinding.FragmentNewCommentBinding;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.dialogs.DatePickerFragment;
import com.nunnos.keepintouch.utils.AlertsManager;

public class NewCommentFragment extends BaseFragmentViewModelLiveData<ContactInfoViewModel, FragmentNewCommentBinding> {
    private final int MAX_LENGHT = 280;
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


    public NewCommentFragment() {
        //Required empty public constructor
    }

    public static NewCommentFragment newInstance() {
        NewCommentFragment fragment = new NewCommentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shareViewModel.retrieveConversations(getContext());
        setView();
        initObservers();
        initListeners();
    }

    private void setView() {
        //TODO: CHECK IF FIRSTTIME OR EDIT

        //----------------------------
        checkLengthAndSetStyle(databinding.newCommentMessage.getText());
    }

    private void initListeners() {
        databinding.newCommentMessage.addTextChangedListener(mTextEditorWatcher);
        databinding.newCommentSaveButton.setOnClickListener(v -> save());
        databinding.newCommentDate.setListener(this::showDatePickerDialog);
    }

    private void save() {
        if (databinding.newCommentMessage.getText().length() <= COVERSATION_MAX_CHARS) {
            getAllDataFromFields();
            if (isEdit) {
                shareViewModel.updateComment(getContext());
            } else {
                shareViewModel.addNewComment(getContext(), shareViewModel.getNewComment());
            }

            shareViewModel.updateOnBack();
            shareViewModel.setNewComment(null);
            shareViewModel.navigateToContactInfo();
        } else {
            AlertsManager.OneButtonAlertListener listener = () -> {
                //Do Nothing
            };
            AlertsManager.showOneButtonAlert(getActivity(),
                    listener,
                    "The comment can't exceed 280 characters",
                    "Accept",
                    true);
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            CustomDate date = new CustomDate(day, month, year);
            final String selectedDate = date.toString();
            shareViewModel.getNewComment().setDate(selectedDate);
            databinding.newCommentDate.setText(selectedDate);
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private void getAllDataFromFields() {
        if (databinding.newCommentDate.getText().equals("DD/MM/YYYY")) {
            shareViewModel.getNewComment().setDate("");
        } else {
            shareViewModel.getNewComment().setDate(databinding.newCommentDate.getText());
        }

    }


    private void initObservers() {
    }

    private void checkLengthAndSetStyle(CharSequence s) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (s.length() <= COMMENT_MAX_CHARS) {
                databinding.newCommentCounter.setTextColor(getContext().getColor(R.color.text_gray));
            } else {
                databinding.newCommentCounter.setTextColor(getContext().getColor(R.color.background_red));
            }
        }
        //This sets a textview to the current length
        databinding.newCommentCounter.setText(s.length() + "/" + COMMENT_MAX_CHARS);
    }
    //Region Base Methods

    @Override
    protected int layout() {
        return R.layout.fragment_new_comment;
    }

    @Override
    protected void dataBindingViewModel() {
        databinding.setShareviewmodel(shareViewModel);
    }

    @Override
    protected boolean isShareViewModel() {
        return true;
    }
}
