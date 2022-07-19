package com.nunnos.keepintouch.presentation.feature.main.fragment.newcontact.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.presentation.feature.main.activity.MainActivity;

public class BackgroundColorPickerFragment extends DialogFragment {
    private MainActivity activity;

    private BGPickerListener listener;

    private FloatingActionButton redBtn;
    private FloatingActionButton pinkBtn;
    private FloatingActionButton purpleBtn;
    private FloatingActionButton purple_darkBtn;
    private FloatingActionButton blue_darkBtn;
    private FloatingActionButton blue_middleBtn;
    private FloatingActionButton blue_lightBtn;
    private FloatingActionButton aqua_lightBtn;
    private FloatingActionButton aqua_darkBtn;
    private FloatingActionButton green_grassBtn;
    private FloatingActionButton green_limeBtn;
    private FloatingActionButton yellow_greenBtn;
    private FloatingActionButton yellow_pureBtn;
    private FloatingActionButton yellow_darkBtn;
    private FloatingActionButton orangeBtn;
    private FloatingActionButton orange_darkBtn;
    private FloatingActionButton brownBtn;
    private FloatingActionButton gray_lightBtn;
    private FloatingActionButton gray_middleBtn;
    private FloatingActionButton gray_darkBtn;

    public BackgroundColorPickerFragment() {
        //Required
    }

    public static BackgroundColorPickerFragment newInstance(BGPickerListener listener) {
        BackgroundColorPickerFragment fragment = new BackgroundColorPickerFragment();
        fragment.setListener(listener);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return createDialog();
    }

    public void setListener(BGPickerListener listener) {
        this.listener = listener;
    }

    private Dialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_background_color_picker, null);
        builder.setView(v);

        bindView(v);
        setListeners();
        return builder.create();
    }

    private void bindView(View v) {
        redBtn = v.findViewById(R.id.bg_color_picker_red);
        pinkBtn = v.findViewById(R.id.bg_color_picker_pink);
        purpleBtn = v.findViewById(R.id.bg_color_picker_purple);
        purple_darkBtn = v.findViewById(R.id.bg_color_picker_purple_dark);
        blue_darkBtn = v.findViewById(R.id.bg_color_picker_blue_dark);
        blue_middleBtn = v.findViewById(R.id.bg_color_picker_blue_middle);
        blue_lightBtn = v.findViewById(R.id.bg_color_picker_blue_light);
        aqua_lightBtn = v.findViewById(R.id.bg_color_picker_aqua_light);
        aqua_darkBtn = v.findViewById(R.id.bg_color_picker_aqua_dark);
        green_grassBtn = v.findViewById(R.id.bg_color_picker_green_grass);
        green_limeBtn = v.findViewById(R.id.bg_color_picker_green_lime);
        yellow_greenBtn = v.findViewById(R.id.bg_color_picker_yellow_green);
        yellow_pureBtn = v.findViewById(R.id.bg_color_picker_yellow_pure);
        yellow_darkBtn = v.findViewById(R.id.bg_color_picker_yellow_dark);
        orangeBtn = v.findViewById(R.id.bg_color_picker_orange);
        orange_darkBtn = v.findViewById(R.id.bg_color_picker_orange_dark);
        brownBtn = v.findViewById(R.id.bg_color_picker_brown);
        gray_lightBtn = v.findViewById(R.id.bg_color_picker_gray_light);
        gray_middleBtn = v.findViewById(R.id.bg_color_picker_gray_middle);
        gray_darkBtn = v.findViewById(R.id.bg_color_picker_gray_dark);
    }

    private void setListeners() {
        redBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_red);
            dismiss();
        });
        pinkBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_pink);
            dismiss();
        });
        purpleBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_purple);
            dismiss();
        });
        purple_darkBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_purple_dark);
            dismiss();
        });
        blue_darkBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_blue_dark);
            dismiss();
        });
        blue_middleBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_blue_middle);
            dismiss();
        });
        blue_lightBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_blue_light);
            dismiss();
        });
        aqua_lightBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_aqua_light);
            dismiss();
        });
        aqua_darkBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_aqua_dark);
            dismiss();
        });
        green_grassBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_green_grass);
            dismiss();
        });
        green_limeBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_green_lime);
            dismiss();
        });
        yellow_greenBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_yellow_green);
            dismiss();
        });
        yellow_pureBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_yellow_pure);
            dismiss();
        });
        yellow_darkBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_yellow_dark);
            dismiss();
        });
        orangeBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_orange);
            dismiss();
        });
        orange_darkBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_orange_dark);
            dismiss();
        });
        brownBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_brown);
            dismiss();
        });
        gray_lightBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_gray_light);
            dismiss();
        });
        gray_middleBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_gray_middle);
            dismiss();
        });
        gray_darkBtn.setOnClickListener(v -> {
            listener.onSelected(R.color.background_gray_dark);
            dismiss();
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.activity = (MainActivity) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractinoListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public interface BGPickerListener{
        void onSelected(int bgSelected);
    }
}
