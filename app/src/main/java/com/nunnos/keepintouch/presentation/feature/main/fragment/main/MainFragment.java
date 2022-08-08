package com.nunnos.keepintouch.presentation.feature.main.fragment.main;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.base.baseviewmodel.EmptyViewModel;
import com.nunnos.keepintouch.databinding.FragmentMainBinding;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.presentation.component.recyclerviews.contactcard.RVContactCardAdapter;
import com.nunnos.keepintouch.presentation.component.recyclerviews.searchcard.RVSearchCardAdapter;
import com.nunnos.keepintouch.presentation.feature.main.activity.vm.MainViewModel;

import java.util.List;

public class MainFragment extends BaseFragmentViewModelLiveData<EmptyViewModel, MainViewModel, FragmentMainBinding> {

    private RVContactCardAdapter adapter;
    private RVSearchCardAdapter searchCardAdapter = null;

    public MainFragment() {
        //Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shareViewModel.retrieveContacts(getContext());
        shareViewModel.retrieveLastIndex(getContext());
        initObservers();
        initListeners();
    }

    private void initObservers() {
        shareViewModel.getContacts().observe(getViewLifecycleOwner(), this::onContactsReceived);
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
        shareViewModel.getContactsFoundByCommentsLD().observe(getViewLifecycleOwner(), this::onFoundByCommentsReceived);
    }

    private void onContactsReceived(List<Contact> contactEntities) {
        setNormalView(contactEntities);
    }

    private void onFoundByNameReceived(List<Contact> contacts) {
        setSearchView(contacts, "By Name");
    }

    private void onFoundByGenderReceived(List<Contact> contacts) {
        setSearchView(contacts, "By Gender");
    }

    private void onFoundBySexualOrientationReceived(List<Contact> contacts) {
        setSearchView(contacts, "By Sexual Orientation");
    }

    private void onFoundByBirthdayReceived(List<Contact> contacts) {
        setSearchView(contacts, "By Birthday");
    }

    private void onFoundByAddressReceived(List<Contact> contacts) {
        setSearchView(contacts, "By Address");
    }

    private void onFoundByProfessionReceived(List<Contact> contacts) {
        setSearchView(contacts, "By Profession");
    }

    private void onFoundByPlaceOfWorkReceived(List<Contact> contacts) {
        setSearchView(contacts, "By PlaceOfWork");
    }

    private void onFoundByHowWeMetReceived(List<Contact> contacts) {
        setSearchView(contacts, "By How We Met");
    }

    private void onFoundByLanguageReceived(List<Contact> contacts) {
        setSearchView(contacts, "By Language");
    }

    private void onFoundByReligionReceived(List<Contact> contacts) {
        setSearchView(contacts, "By Religion");
    }

    private void onFoundByRelativesReceived(List<Contact> contacts) {
        setSearchView(contacts, "By Relatives");
    }

    private void onFoundByConversationsReceived(List<Contact> contacts) {
        setSearchView(contacts, "By Conversations");
    }

    private void onFoundByAliasReceived(List<Contact> contacts) {
        setSearchView(contacts, "By Alias");
    }

    private void onFoundByTelephoneReceived(List<Contact> contacts) {
        setSearchView(contacts, "By Telephone");
    }

    private void onFoundByEmailReceived(List<Contact> contacts) {
        setSearchView(contacts, "By Email");
    }

    private void onFoundByCommentsReceived(List<Contact> contacts) {
        setSearchView(contacts, "By Comments");
    }


    private void initListeners() {
        databinding.mainAddImageButton.setOnClickListener(v -> shareViewModel.navigateToNewContact());
        databinding.mainSearcher.setOnEditorActionListener((v, actionId, event) -> {
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
        if (databinding.mainSearcher.getText().toString().isEmpty()) {
            shareViewModel.retrieveContacts(getContext());
            return;
        }
        String subject = databinding.mainSearcher.getText().toString();
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
        shareViewModel.searchByComments(getContext(), subject);
    }

    private void setNormalView(List<Contact> contactList) {
        adapter = new RVContactCardAdapter(contactList);
        databinding.mainRecyclerView.setAdapter(adapter);
        databinding.mainRecyclerView.setHasFixedSize(false);
    }

    private void setSearchView(List<Contact> contactList, String title) {
        if (searchCardAdapter == null) {
            searchCardAdapter = new RVSearchCardAdapter(contactList, title);
        } else {
            searchCardAdapter.addItems(contactList, title);
        }
        databinding.mainRecyclerView.setAdapter(searchCardAdapter);
        searchCardAdapter.notifyDataSetChanged();
        databinding.mainRecyclerView.setHasFixedSize(false);
    }

    //Region Base Methods
    @Override
    protected int layout() {
        return R.layout.fragment_main;
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