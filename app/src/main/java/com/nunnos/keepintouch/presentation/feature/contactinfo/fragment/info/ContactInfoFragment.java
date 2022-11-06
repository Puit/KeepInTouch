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
import com.nunnos.keepintouch.presentation.component.recyclerviews.conversationimportant.RVConversationImportantAdapter;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.utils.FileManager;
import com.nunnos.keepintouch.utils.ImageHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ContactInfoFragment extends BaseFragmentViewModelLiveData<ContactInfoViewModel, FragmentContactInfoBinding> {
    private RVConversationImportantAdapter adapter;

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
        shareViewModel.retrieveConversations(getContext());
        shareViewModel.retrieveComments(getContext());
    }

    private void onConversationsReceived(List<Conversation> conversations) {
        setConversationsInfo(conversations);
    }

    private void onCommentsReceived(List<Comment> comments) {
        shareViewModel.addCommentToComplements(comments);
        setConversationToRecyclerView();
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
            databinding.contactInfoChatQuantity.setText(String.valueOf(conversations.size()));
            if (!mostRecentConversation.getPlace().isEmpty()) {
                databinding.contactInfoLocation.setVisibility(View.VISIBLE);
                databinding.contactInfoLocationIcon.setVisibility(View.VISIBLE);
                databinding.contactInfoLocation.setText(mostRecentConversation.getPlace());
            }
            shareViewModel.addConversationToComplements(conversations);
            setConversationToRecyclerView();
        }
    }

    private void setConversationToRecyclerView() {
        RVConversationImportantAdapter.CustomClick listener = complement -> {
            if (complement instanceof Conversation) {
                shareViewModel.setNewConversation((Conversation) complement);
                shareViewModel.showNewConversationFragment();
            } else if (complement instanceof Comment) {
                shareViewModel.setNewComment((Comment) complement);
                shareViewModel.showNewComment();
            }

        };
        if (adapter == null) {
            adapter = new RVConversationImportantAdapter(shareViewModel.getComplements(), listener);
            databinding.contactInfoRecyclerviewChats.setAdapter(adapter);
            databinding.contactInfoRecyclerviewChats.setHasFixedSize(false);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void setContactInfo(Contact contact) {
        setFavImage();
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
                    Date cDate = new SimpleDateFormat("dd/MM/yyyy").parse(c.getDate().replaceAll(" ", "").trim());
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
            return null; //ENTRA AQUI
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
        databinding.contactInfoAddComment.setOnClickListener(v -> shareViewModel.showNewComment());
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
    public void onPause() {
        shareViewModel.removeComplements();
//        adapter.notifyDataSetChanged();
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
