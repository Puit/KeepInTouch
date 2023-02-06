package com.nunnos.keepintouch.presentation.feature.contactinfo.fragment.personaldata;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.databinding.FragmentContactPersonalDataBinding;
import com.nunnos.keepintouch.domain.model.Contact;
import com.nunnos.keepintouch.presentation.component.CustomTextView;
import com.nunnos.keepintouch.presentation.component.recyclerviews.contactsselector.RVContactAdapter;
import com.nunnos.keepintouch.presentation.component.recyclerviews.searchcard.RVSearchCardAdapter;
import com.nunnos.keepintouch.presentation.feature.contactinfo.activity.vm.ContactInfoViewModel;
import com.nunnos.keepintouch.utils.FileManager;
import com.nunnos.keepintouch.utils.ImageHelper;
import com.nunnos.keepintouch.utils.TextUtils;

import java.util.List;

public class ContactPersonalDataFragment extends BaseFragmentViewModelLiveData<ContactInfoViewModel, FragmentContactPersonalDataBinding> {

    private RVSearchCardAdapter contactsAdapter = null;

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
        if (c.isRealBirthday()) {
            setTextOrHide(databinding.contactPersonalDataBirthday, c.getBirthday());
            setTextOrHide(databinding.contactPersonalDataAge, String.valueOf(c.getAge()));
        } else {
            databinding.contactPersonalDataBirthday.setVisibility(View.GONE);
            setTextOrHide(databinding.contactPersonalDataAge, String.valueOf(c.getAge()));
        }
        setTextOrHide(databinding.contactPersonalDataAdress, c.getAddress());
        setTextOrHide(databinding.contactPersonalDataProfession, c.getProfession());
        setTextOrHide(databinding.contactPersonalDataPlaceofwork, c.getPlaceOfWork());
        setTextOrHide(databinding.contactPersonalDataHowWeMet, c.getHowWeMet());
        setTextOrHide(databinding.contactPersonalDataLanguage, c.getLanguage());
        setTextOrHide(databinding.contactPersonalDataReligion, c.getReligion());

        setTextOrHide(databinding.contactPersonalDataSocialMedia, c.getSocialMedia());
        setUserImage(c);
        setRelatives(shareViewModel.getThisContactRelatives());
    }

    private void setRelatives(List<Contact> relativesList) {
        if(relativesList.size() == 0){
            databinding.contactPersonalDataRelativesRecyclerView.setVisibility(View.GONE);
            databinding.contactPersonalDataRelativesTitle.setVisibility(View.GONE);
            return;
        }
        databinding.contactPersonalDataRelativesRecyclerView.setVisibility(View.VISIBLE);
        contactsAdapter = new RVSearchCardAdapter(relativesList, null);
        databinding.contactPersonalDataRelativesRecyclerView.setAdapter(contactsAdapter);
        contactsAdapter.notifyDataSetChanged();
        databinding.contactPersonalDataRelativesRecyclerView.setHasFixedSize(false);
    }

    private void setTextOrHide(TextView tv, String text) {
        if (TextUtils.isEmpty(text) || text.equals(getString(R.string.unknown_value)) || text.equals(getString(R.string.date_format))) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }

    private void setTextOrHide(CustomTextView tv, String text) {
        if (TextUtils.isEmpty(text) || text.equals(getString(R.string.unknown_value)) || text.equals(getString(R.string.date_format))) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }

    private void setTextOrHide(CustomTextView tv, String text, int src) {
        if (TextUtils.isEmpty(text) || text.equals(getString(R.string.unknown_value)) || text.equals(getString(R.string.date_format))) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
            tv.setImage(src);
        }
    }

    private void setUserImage(Contact contact) {
        Bitmap bitmap = FileManager.getBitmapPhoto(contact.getPhoto());
        if (bitmap == null) {
            databinding.contactPersonalDataImage.setImageDrawable(getContext().getDrawable(R.drawable.ic_person_full));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                databinding.contactPersonalDataImage.setBackgroundColor(getContext().getColor(R.color.text_gray));
            }
        } else {
            databinding.contactPersonalDataImage.setImageBitmap(bitmap);
            ImageHelper.resizeImage(databinding.contactPersonalDataImage, FileManager.getBitmapPhoto(contact.getPhoto()));
            databinding.contactPersonalDataImage.setRotation(contact.getAngle());
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
