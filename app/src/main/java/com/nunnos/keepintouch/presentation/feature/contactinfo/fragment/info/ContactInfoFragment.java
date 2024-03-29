package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.info;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.databinding.FragmentContactInfoBinding;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.domain.model.complements.Comment;
import com.nunnos.keepintouch.domain.model.complements.Conversation;
import com.nunnos.keepintouch.presentation.component.recyclerviews.conversationimportant.RVComentAdapter;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.utils.FileManager;
import com.nunnos.keepintouch.utils.ImageHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ContactInfoFragment extends BaseFragmentViewModelLiveData<ContactInfoViewModel, FragmentContactInfoBinding> {
    private RVComentAdapter adapter;

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
        shareViewModel.getConversations().observe(getViewLifecycleOwner(), this::onConversationsReceived);
        shareViewModel.getComments().observe(getViewLifecycleOwner(), this::onCommentsReceived);
    }

    private void onContactReceived(Contact contact) {
        setContactInfo(contact);
        shareViewModel.retrieveComments(getContext());
    }

    private void onConversationsReceived(List<Conversation> conversations) {
        setConversationsInfo(conversations);
    }

    private void onCommentsReceived(List<Comment> comments) {
        setCommentsToRecyclerView();
        shareViewModel.retrieveConversations(getContext());
    }

    private void setConversationsInfo(List<Conversation> conversations) {
        if (conversations != null && conversations.size() > 0) {
            Conversation mostRecentConversation = getMostRecentConverstion(conversations);
            if (mostRecentConversation == null) return;
            databinding.contactInfoLastChatIcon.setVisibility(View.VISIBLE);
            databinding.contactInfoChatIcon.setVisibility(View.VISIBLE);
            databinding.contactInfoChatQuantity.setVisibility(View.VISIBLE);
            databinding.contactInfoLastChatDate.setVisibility(View.VISIBLE);
            databinding.contactInfoLastChatDate.setText(mostRecentConversation.getDate());
            databinding.contactInfoChatQuantity.setText(conversations.size() + " " + getString(R.string.chats));
            if (!mostRecentConversation.getPlace().isEmpty()) {
                databinding.contactInfoLocation.setVisibility(View.VISIBLE);
                databinding.contactInfoLocationIcon.setVisibility(View.VISIBLE);
                databinding.contactInfoLocation.setText(mostRecentConversation.getPlace());
            }
        }
    }

    private void setCommentsToRecyclerView() {
        RVComentAdapter.CustomClick listener = comment -> {
            shareViewModel.setNewComment(comment);
            shareViewModel.showNewComment();
        };
        if (adapter == null) {
            adapter = new RVComentAdapter(shareViewModel.getComments().getValue(), listener);
            databinding.contactInfoRecyclerviewComments.setAdapter(adapter);
            databinding.contactInfoRecyclerviewComments.setHasFixedSize(false);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void setContactInfo(Contact contact) {
        setUserImage(contact);
        databinding.contactInfoName.setText(contact.getName());
        if (contact.getAlias().isEmpty()) {
            databinding.contactInfoAlias.setText(contact.getAlias());
            databinding.contactInfoAlias.setVisibility(View.VISIBLE);
        }
    }

    private Conversation getMostRecentConverstion(List<Conversation> conversations) {
        Date lastChatDate = null;
        Conversation mostRecentConversation = null;
        try {
            for (Conversation c : conversations) {
                if (!c.getDate().isEmpty()) {
                    Date cDate = new SimpleDateFormat(getString(R.string.date_format)).parse(c.getDate().replaceAll(" ", "").trim());
                    if (lastChatDate == null) {
                        lastChatDate = cDate;
                        mostRecentConversation = c;
                    } else {
                        if (lastChatDate.compareTo(cDate) < 0) {
                            lastChatDate = cDate;
                            mostRecentConversation = c;
                        }
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (lastChatDate == null) {
            return null;
        }
        return mostRecentConversation;
    }


    private void setUserImage(Contact contact) {
        Bitmap bitmap = FileManager.getBitmapPhoto(contact.getPhoto());
        if (bitmap == null) {
            databinding.contactInfoImage.setImageDrawable(getContext().getDrawable(R.drawable.ic_person_full));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                databinding.contactInfoImage.setBackgroundColor(getContext().getColor(R.color.text_gray));
            }
        } else {
            databinding.contactInfoImage.setImageBitmap(bitmap);
            ImageHelper.resizeImage(databinding.contactInfoImage, FileManager.getBitmapPhoto(contact.getPhoto()));
            databinding.contactInfoImage.setRotation(contact.getAngle());
        }
    }

    private void initListeners() {
        databinding.contactInfoAddComment.setOnClickListener(__ -> shareViewModel.showNewComment());
    }

    //Region Base Methods

    @Override
    public void onPause() {
//        shareViewModel.removeComplements();
        super.onPause();
    }

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
