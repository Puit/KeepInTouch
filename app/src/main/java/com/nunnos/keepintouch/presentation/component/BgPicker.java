package com.nunnos.keepintouch.presentation.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.databinding.ComponentBgPickerBinding;

public class BgPicker extends ConstraintLayout {
    ComponentBgPickerBinding databinding;
    private CustomListener listener;
    private int colorId;

    public BgPicker(@NonNull Context context) {
        this(context, null);
    }

    public BgPicker(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bindView();
        setListener();
        parseAttributes(attrs);
    }

    private void setListener() {
        databinding.bgPickerImage.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick();
            }
        });
    }

    private void parseAttributes(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.BgPicker, 0, 0);
        int colorId = attributes.getColor(R.styleable.BgPicker_color_id, -1);
        String hintText = attributes.getString(R.styleable.BgPicker_edit_text_hint);

        boolean clickable = attributes.getBoolean(R.styleable.BgPicker_image_clickable, true);
        boolean focusable = attributes.getBoolean(R.styleable.BgPicker_image_focusable, true);

        if (!TextUtils.isEmpty(hintText)) {
            databinding.bgPickerHint.setHint(hintText);
        }
        if (colorId != -1) {
            this.colorId = colorId;
            databinding.bgPickerImage.setBackgroundColor(colorId);
        }
        databinding.bgPickerHint.setClickable(clickable);
        databinding.bgPickerImage.setClickable(clickable);
        databinding.bgPickerHint.setFocusable(focusable);
        databinding.bgPickerImage.setFocusable(focusable);
    }

    public void onFocusChanged(View.OnFocusChangeListener l) {
        databinding.bgPickerImage.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                l.onFocusChange(v, hasFocus);
            }
        });
    }

    private void bindView() {
        databinding = ComponentBgPickerBinding.inflate(LayoutInflater.from(getContext()), this, true);
    }

    public void setListener(CustomListener l) {
        this.listener = l;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int id) {
        colorId = id;
        databinding.bgPickerImage.setImageResource(id);
    }

    public interface CustomListener {
        void onItemClick();
    }
}
