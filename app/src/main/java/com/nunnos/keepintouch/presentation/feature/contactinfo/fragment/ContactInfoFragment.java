package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.databinding.FragmentContactInfoBinding;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.domain.model.Conversation;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.vm.ContactInfoFragmentViewModel;
import com.nunnos.keepintouch.utils.FileManager;
import com.nunnos.keepintouch.utils.ImageHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ContactInfoFragment extends BaseFragmentViewModelLiveData<ContactInfoFragmentViewModel, ContactInfoViewModel, FragmentContactInfoBinding> {

    public ContactInfoFragment() {
        //Required empty public constructor
    }

    public static ContactInfoFragment newInstance() {
        ContactInfoFragment fragment = new ContactInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObservers();
        initListeners();
    }

    private void initObservers() {
        shareViewModel.getThisContact().observe(getViewLifecycleOwner(), this::onContactReceived);
    }

    private void onContactReceived(Contact contact) {
        setView(contact);
    }

    private void setView(Contact contact) {
        setFavImage();
        setUserImage(contact);
        databinding.contactInfoName.setText(contact.getName());
        if (contact.getAlias().isEmpty()) {
            databinding.contactInfoAlias.setText(contact.getAlias());
            databinding.contactInfoAlias.setVisibility(View.VISIBLE);
        }
        if (shareViewModel.getConversations().getValue() != null && shareViewModel.getConversations().getValue().size() > 0) {
            databinding.contactInfoChatIcon.setVisibility(View.VISIBLE);
            databinding.contactInfoChatQuantity.setVisibility(View.VISIBLE);
            databinding.contactInfoLastChatIcon.setVisibility(View.VISIBLE);
            databinding.contactInfoLastChat.setVisibility(View.VISIBLE);
            databinding.contactInfoLastChat.setText(getLastChatDate(shareViewModel.getConversations().getValue()));
            databinding.contactInfoChatQuantity.setText(String.valueOf(shareViewModel.getConversations().getValue().size()));
        }
    }

    private String getLastChatDate(List<Conversation> conversations) {
        Date lastChatDate = null;
        try {
            for (Conversation c : conversations) {
                Date cDate = new SimpleDateFormat("dd/MM/yyyy").parse(c.getDate());
                if (lastChatDate == null) {
                    lastChatDate = cDate;
                } else {
                    if (lastChatDate.compareTo(cDate) < 0) {
                        lastChatDate = cDate;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (lastChatDate == null) {
            return "";
        }
        return lastChatDate.toString(); //TODO: MIRAR FORMAT
    }

    private void setUserImage(Contact contact) {
        Bitmap bitmap = FileManager.getBitmapPhoto(contact.getPhoto());
        databinding.contactInfoImage.setImageBitmap(bitmap);
        ImageHelper.resizeImage(databinding.contactInfoImage, FileManager.getBitmapPhoto(contact.getPhoto()));
        databinding.contactInfoImage.setRotation(contact.getAngle());
    }

    private void setFavImage() {
        if (shareViewModel.getThisContact().getValue() == null) return;
        if (shareViewModel.getThisContact().getValue().isFavorite()) {
            databinding.contactInfoFavImage.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_favorite_red));
        } else {
            databinding.contactInfoFavImage.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_favorite_border_red));
        }
        shareViewModel.updateOnBack();
    }

    private void initListeners() {
        databinding.contactInfoFavRounder.setOnClickListener(v -> favContact());
    }

    private void favContact() {
        if (shareViewModel.getThisContact().getValue().isFavorite()) {
            shareViewModel.getThisContact().getValue().setFavorite(false);
        } else {
            shareViewModel.getThisContact().getValue().setFavorite(true);
        }
        shareViewModel.updateThisContact(getContext());
        setFavImage();
    }

    //Region Base Methods

    @Override
    protected int layout() {
        return R.layout.fragment_contact_info;
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
