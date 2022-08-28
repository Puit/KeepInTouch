package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.personaldata;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.databinding.FragmentContactPersonalDataBinding;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.presentation.component.CustomTextView;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.vm.ContactPersonalDataViewModel;

import java.util.Arrays;
import java.util.List;

public class ContactPersonalDataFragment extends BaseFragmentViewModelLiveData<ContactPersonalDataViewModel, ContactInfoViewModel, FragmentContactPersonalDataBinding> {

    public ContactPersonalDataFragment() {
        //Required empty public constructor
    }

    public static ContactPersonalDataFragment newInstance() {
        ContactPersonalDataFragment fragment = new ContactPersonalDataFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView();
        initListeners();
    }

    private void initListeners() {
        databinding.contactPersonalDataFavRounder.setOnClickListener(v -> shareViewModel.showEditContactFragment());
    }

    private void setView() {
        Contact c = shareViewModel.getThisContact().getValue();

        if (c.getFullName().isEmpty() && !c.getAlias().isEmpty()) {
            setTextOrHide(databinding.contactPersonalDataName, c.getAlias());
            databinding.contactPersonalDataAlias.setVisibility(View.GONE);
        } else {
            setTextOrHide(databinding.contactPersonalDataName, c.getFullName());
            setTextOrHide(databinding.contactPersonalDataAlias, c.getAlias());
        }
        setTextOrHide(databinding.contactPersonalDataTelephone, c.getTelephone());
        setTextOrHide(databinding.contactPersonalDataEmail, c.getEmail());
        setTextOrHide(databinding.contactPersonalDataGender, c.getGender());
        setTextOrHide(databinding.contactPersonalDataSexualOrientation, c.getSexualOrientation());
        if(c.isRealBirthday()){
            setTextOrHide(databinding.contactPersonalDataBirthday, c.getBirthday());
            setTextOrHide(databinding.contactPersonalDataAge, String.valueOf(c.getAge()));
        }else{
            databinding.contactPersonalDataBirthday.setVisibility(View.GONE);
            setTextOrHide(databinding.contactPersonalDataAge, c.getBirthday());
        }
        setTextOrHide(databinding.contactPersonalDataAdress, c.getAddress());
        setTextOrHide(databinding.contactPersonalDataProfession, c.getProfession());
        setTextOrHide(databinding.contactPersonalDataPlaceofwork, c.getPlaceOfWork());
        setTextOrHide(databinding.contactPersonalDataHowWeMet, c.getHowWeMet());
        setTextOrHide(databinding.contactPersonalDataLanguage, c.getLanguage());
        setTextOrHide(databinding.contactPersonalDataReligion, c.getReligion());
        setTextOrHide(databinding.contactPersonalDataRelatives, c.getRelatives());
        setTextOrHide(databinding.contactPersonalDataSocialMedia, c.getSexualOrientation());
    }

    private void setTextOrHide(TextView tv, String text) {
        if (text.isEmpty() || text.equals("Unknown")|| text.equals("DD/MM/YYYY")) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }
    private void setTextOrHide(CustomTextView tv, String text) {
        if (text.isEmpty() || text.equals("Unknown")|| text.equals("DD/MM/YYYY")) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }
    private void setTextOrHide(CustomTextView tv, String text, int src) {
        if (text.isEmpty() || text.equals("Unknown")|| text.equals("DD/MM/YYYY")) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
            tv.setImage(src);
        }
    }

    //Region Base Methods

    @Override
    protected int layout() {
        return R.layout.fragment_contact_personal_data;
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
