package com.nunnos.keepintouch.presentation.feature.main.fragment.search;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.databinding.FragmentSearchContactBinding;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.presentation.component.recyclerviews.searchcard.RVSearchCardAdapter;
import com.nunnos.keepintouch.presentation.feature.main.activity.vm.MainViewModel;

import java.util.List;

public class SearchContactFragment extends BaseFragmentViewModelLiveData<MainViewModel, FragmentSearchContactBinding> {
    private RVSearchCardAdapter searchCardAdapter = null;

    public SearchContactFragment() {
        //Required empty public constructor
    }

    public static SearchContactFragment newInstance() {
        SearchContactFragment fragment = new SearchContactFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView();
        initObservers();
        initListeners();
    }

    private void setView() {
        databinding.searchContactSearcher.requestFocus();

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(databinding.searchContactSearcher, InputMethodManager.SHOW_IMPLICIT);
    }

    private void initObservers() {
        shareViewModel.getContactsFoundByNameLD().observe(getViewLifecycleOwner(), this::onFoundByNameReceived);
        shareViewModel.getContactsFoundByGenderLD().observe(getViewLifecycleOwner(), this::onFoundByGenderReceived);
        shareViewModel.getContactsFoundBySexualOrientationLD().observe(getViewLifecycleOwner(), this::onFoundBySexualOrientationReceived);
        shareViewModel.getContactsFoundByBirthdayLD().observe(getViewLifecycleOwner(), this::onFoundByBirthdayReceived);
        shareViewModel.getContactsFoundByAddressLD().observe(getViewLifecycleOwner(), this::onFoundByAddressReceived);
        shareViewModel.getContactsFoundByProfessionLD().observe(getViewLifecycleOwner(), this::onFoundByProfessionReceived);
        shareViewModel.getContactsFoundByPlaceOfWorkLD().observe(getViewLifecycleOwner(), this::onFoundByPlaceOfWorkReceived);
        shareViewModel.getContactsFoundByHowWeMetLD().observe(getViewLifecycleOwner(), this::onFoundByHowWeMetReceived);
        shareViewModel.getContactsFoundByLanguageLD().observe(getViewLifecycleOwner(), this::onFoundByLanguageReceived);
        shareViewModel.getContactsFoundByReligionLD().observe(getViewLifecycleOwner(), this::onFoundByReligionReceived);
        shareViewModel.getContactsFoundByRelativesLD().observe(getViewLifecycleOwner(), this::onFoundByRelativesReceived);
        shareViewModel.getContactsFoundByConversationsLD().observe(getViewLifecycleOwner(), this::onFoundByConversationsReceived);
        shareViewModel.getContactsFoundByAliasLD().observe(getViewLifecycleOwner(), this::onFoundByAliasReceived);
        shareViewModel.getContactsFoundByTelephoneLD().observe(getViewLifecycleOwner(), this::onFoundByTelephoneReceived);
        shareViewModel.getContactsFoundByEmailLD().observe(getViewLifecycleOwner(), this::onFoundByEmailReceived);
        shareViewModel.getContactsFoundBySocialMediaLD().observe(getViewLifecycleOwner(), this::onFoundBySocialMediaReceived);
        shareViewModel.getContactsFoundByCommentsLD().observe(getViewLifecycleOwner(), this::onFoundByCommentsReceived);
    }

    private void initListeners() {
        databinding.searchContactSearcher.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            searchCardAdapter = null;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                searchOnDB();
                handled = true;
            }
            return handled;
        });
    }

    private void searchOnDB() {
        if (databinding.searchContactSearcher.getText().toString().isEmpty()) {
            shareViewModel.retrieveContacts(getContext());
            return;
        }
        String subject = databinding.searchContactSearcher.getText().toString();
        shareViewModel.searchByName(getContext(), subject);
        shareViewModel.searchByGender(getContext(), subject);
        shareViewModel.searchBySexualOrientation(getContext(), subject);
        shareViewModel.searchByBirthday(getContext(), subject);
        shareViewModel.searchByAddress(getContext(), subject);
        shareViewModel.searchByProfession(getContext(), subject);
        shareViewModel.searchByPlaceOfWork(getContext(), subject);
        shareViewModel.searchByHowWeMet(getContext(), subject);
        shareViewModel.searchByLanguage(getContext(), subject);
        shareViewModel.searchByReligion(getContext(), subject);
        shareViewModel.searchByRelatives(getContext(), subject);
        shareViewModel.searchByConversations(getContext(), subject);
        shareViewModel.searchByAlias(getContext(), subject);
        shareViewModel.searchByTelephone(getContext(), subject);
        shareViewModel.searchByEmail(getContext(), subject);
        shareViewModel.searchBySocialMedia(getContext(), subject);
        shareViewModel.searchByComments(getContext(), subject);
    }

    private void onFoundByNameReceived(List<Contact> contacts) {
        setSearchView(contacts, databinding.searchContactRvName, databinding.searchContactName);
    }

    private void onFoundByGenderReceived(List<Contact> contacts) {
        setSearchView(contacts, databinding.searchContactRvGender, databinding.searchContactGender);
    }

    private void onFoundBySexualOrientationReceived(List<Contact> contacts) {
        setSearchView(contacts, databinding.searchContactRvSexualOrientation, databinding.searchContactSexualOrientation);
    }

    private void onFoundByBirthdayReceived(List<Contact> contacts) {
        setSearchView(contacts, databinding.searchContactRvBirthday, databinding.searchContactBirthday);
    }

    private void onFoundByAddressReceived(List<Contact> contacts) {
        setSearchView(contacts, databinding.searchContactRvAddress, databinding.searchContactAddress);
    }

    private void onFoundByProfessionReceived(List<Contact> contacts) {
        setSearchView(contacts, databinding.searchContactRvProfession, databinding.searchContactProfession);
    }

    private void onFoundByPlaceOfWorkReceived(List<Contact> contacts) {
        setSearchView(contacts, databinding.searchContactRvPlaceOfWork, databinding.searchContactPlaceOfWork);
    }

    private void onFoundByHowWeMetReceived(List<Contact> contacts) {
        setSearchView(contacts, databinding.searchContactRvHowWeMet, databinding.searchContactHowWeMet);
    }

    private void onFoundByLanguageReceived(List<Contact> contacts) {
        setSearchView(contacts, databinding.searchContactRvLanguage, databinding.searchContactLanguage);
    }

    private void onFoundByReligionReceived(List<Contact> contacts) {
        setSearchView(contacts, databinding.searchContactRvReligion, databinding.searchContactReligion);
    }

    private void onFoundByRelativesReceived(List<Contact> contacts) {
        setSearchView(contacts, databinding.searchContactRvRelatives, databinding.searchContactRelatives);
    }

    private void onFoundByConversationsReceived(List<Contact> contacts) {
        setSearchView(contacts, databinding.searchContactRvConversations, databinding.searchContactConversations);
    }

    private void onFoundByAliasReceived(List<Contact> contacts) {
        setSearchView(contacts, databinding.searchContactRvAlias, databinding.searchContactAlias);
    }

    private void onFoundByTelephoneReceived(List<Contact> contacts) {
        setSearchView(contacts, databinding.searchContactRvTelephone, databinding.searchContactTelephone);
    }

    private void onFoundByEmailReceived(List<Contact> contacts) {
        setSearchView(contacts, databinding.searchContactRvEmail, databinding.searchContactEmail);
    }
    private void onFoundBySocialMediaReceived(List<Contact> contacts) {
        setSearchView(contacts, databinding.searchContactRvSocialMedia, databinding.searchContactSocialMedia);
    }

    private void onFoundByCommentsReceived(List<Contact> contacts) {
        setSearchView(contacts, databinding.searchContactRvComments, databinding.searchContactComments);
    }

    private void setSearchView(List<Contact> contactList, RecyclerView recyclerView, TextView textView) {
        if (contactList == null || contactList.isEmpty() || contactList.size() < 1) {
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        if (searchCardAdapter == null) {
            RVSearchCardAdapter.CustomItemClick contactListener = (contactId) -> {
                shareViewModel.setContactSelectedID(contactId);
                shareViewModel.navigateToContactInfo();
                shareViewModel.clearSearch();
                getActivity().onBackPressed();
            };

            searchCardAdapter = new RVSearchCardAdapter(contactList, contactListener);
        } else {
            searchCardAdapter.addItems(contactList);
        }
        recyclerView.setAdapter(searchCardAdapter);
        searchCardAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(false);
    }

    private void clearAll() {
        clearAndHide(databinding.searchContactRvName, databinding.searchContactName);
        clearAndHide(databinding.searchContactRvGender, databinding.searchContactGender);
        clearAndHide(databinding.searchContactRvSexualOrientation, databinding.searchContactSexualOrientation);
        clearAndHide(databinding.searchContactRvBirthday, databinding.searchContactBirthday);
        clearAndHide(databinding.searchContactRvAddress, databinding.searchContactAddress);
        clearAndHide(databinding.searchContactRvProfession, databinding.searchContactProfession);
        clearAndHide(databinding.searchContactRvPlaceOfWork, databinding.searchContactPlaceOfWork);
        clearAndHide(databinding.searchContactRvHowWeMet, databinding.searchContactHowWeMet);
        clearAndHide(databinding.searchContactRvLanguage, databinding.searchContactLanguage);
        clearAndHide(databinding.searchContactRvReligion, databinding.searchContactReligion);
        clearAndHide(databinding.searchContactRvRelatives, databinding.searchContactRelatives);
        clearAndHide(databinding.searchContactRvConversations, databinding.searchContactConversations);
        clearAndHide(databinding.searchContactRvAlias, databinding.searchContactAlias);
        clearAndHide(databinding.searchContactRvTelephone, databinding.searchContactTelephone);
        clearAndHide(databinding.searchContactRvEmail, databinding.searchContactEmail);
        clearAndHide(databinding.searchContactRvComments, databinding.searchContactComments);
    }

    private void clearAndHide(RecyclerView recyclerView, TextView textView) {
        recyclerView.setAdapter(null);
        searchCardAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(false);

        recyclerView.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //Region Base Methods
    @Override
    protected int layout() {
        return R.layout.fragment_search_contact;
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
