package com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.dialogs;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.BaseFragmentViewModelLiveData;
import com.nunnos.keepintouch.base.baseviewmodel.EmptyViewModel;
import com.nunnos.keepintouch.databinding.FragmentRecyclerviewBinding;
import com.nunnos.keepintouch.presentation.component.recyclerviews.itemtext.ImageAndText;
import com.nunnos.keepintouch.presentation.component.recyclerviews.itemtext.RVITAdapter;
import com.nunnos.keepintouch.presentation.feature.main.activity.vm.MainViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenderPickerFragment extends BaseFragmentViewModelLiveData<EmptyViewModel, MainViewModel, FragmentRecyclerviewBinding> {

    private RVITAdapter adapter;

    public GenderPickerFragment() {
        //Required empty public constructor
    }

    public static GenderPickerFragment newInstance() {
        GenderPickerFragment fragment = new GenderPickerFragment();
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
        databinding.recyclerViewFragmentTitle.setText("Gender");
        RVITAdapter.RVITAdapterViewHolder.CustomItemClick listener = new RVITAdapter.RVITAdapterViewHolder.CustomItemClick() {
            @Override
            public void onItemClick(String gender, Drawable icon) {
                //Set gender to shareviewModel.contact
                Toast.makeText(getContext(), gender, Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        };

//        adapter = new RVITAdapter(createGendersList(), listener, databinding.);
        databinding.recyclerViewFragmentRecyclerView.setAdapter(adapter);
        databinding.recyclerViewFragmentRecyclerView.setHasFixedSize(false);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private List<ImageAndText> createGendersList() {
        ArrayList<ImageAndText> resultList = new ArrayList<>();
        List<String> gendersList = Arrays.asList(getResources().getStringArray(R.array.genders));
        for (int i = 0; i < gendersList.size(); i++) {
            switch (i) {
                case 0:
                    resultList.add(new ImageAndText(getContext().getDrawable(R.drawable.ic_male), gendersList.get(i)));
                    break;
                case 1:
                    resultList.add(new ImageAndText(getContext().getDrawable(R.drawable.ic_female), gendersList.get(i)));
                    break;
                case 2:
                    resultList.add(new ImageAndText(getContext().getDrawable(R.drawable.ic_non_binary), gendersList.get(i)));
                    break;
                case 3:
                    resultList.add(new ImageAndText(getContext().getDrawable(R.drawable.ic_transgender), gendersList.get(i)));
                    break;
                case 4:
                    resultList.add(new ImageAndText(getContext().getDrawable(R.drawable.ic_unknown), gendersList.get(i)));
                    break;
            }
        }
        return resultList;
    }

    //Region Base Methods
    @Override
    protected int layout() {
        return R.layout.fragment_recyclerview;
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