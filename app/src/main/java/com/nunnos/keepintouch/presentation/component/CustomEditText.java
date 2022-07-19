package com.nunnos.keepintouch.presentation.component;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.databinding.ComponentCustomEditTextBinding;

public class CustomEditText extends ConstraintLayout {
    ComponentCustomEditTextBinding databinding;
    private CustomListener listener;
    private int errorColor;
    private int successColor;
    private String errorText;

    public CustomEditText(@NonNull Context context) {
        this(context, null);
    }

    public CustomEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bindView();
        setListener();
        parseAttributes(attrs);
    }

    private void setListener() {
        databinding.etTitle.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick();
            }
        });
    }

    private void parseAttributes(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditText, 0, 0);
        String text = attributes.getString(R.styleable.CustomEditText_edit_text_text);
        String hintText = attributes.getString(R.styleable.CustomEditText_edit_text_hint_text);
        errorText = attributes.getString(R.styleable.CustomEditText_edit_text_error_text);
        int textColor = attributes.getColor(R.styleable.CustomEditText_edit_text_text_color, getContext().getColor(R.color.black));
        int hintTextColorAtEndPosition = attributes.getColor(R.styleable.CustomEditText_edit_text_hint_text_color_at_end_position, getContext().getColor(R.color.background_gray_middle));
        int hintTextColorAtStartPosition = attributes.getColor(R.styleable.CustomEditText_edit_text_hint_text_color_at_start_position, getContext().getColor(R.color.background_gray_dark));
        int underLineColor = attributes.getColor(R.styleable.CustomEditText_edit_text_underline_color, getContext().getColor(R.color.background_purple));
        boolean clickable = attributes.getBoolean(R.styleable.CustomEditText_edit_text_clickable, true);
        boolean focusable = attributes.getBoolean(R.styleable.CustomEditText_edit_text_focusable, true);
        if (!TextUtils.isEmpty(hintText)) {
            databinding.tilTitle.setHint(hintText);
        }
        if (!TextUtils.isEmpty(text)) {
            databinding.etTitle.setText(text);
        }
        databinding.tilTitle.setClickable(clickable);
        databinding.etTitle.setClickable(clickable);
        databinding.tilTitle.setFocusable(focusable);
        databinding.etTitle.setFocusable(focusable);
        databinding.etTitle.setTextColor(textColor);
        databinding.tilTitle.setHintTextColor(ColorStateList.valueOf(hintTextColorAtEndPosition));
        databinding.tilTitle.setHelperTextColor(ColorStateList.valueOf(hintTextColorAtStartPosition));
        databinding.tilTitle.setBoxStrokeColorStateList(ColorStateList.valueOf(underLineColor));

    }

    public void onFocusChanged(View.OnFocusChangeListener l) {
        databinding.etTitle.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                l.onFocusChange(v, hasFocus);
            }
        });
    }

    public void setErrorState() {
        databinding.tilTitle.setError(errorText);
    }

    public void setSuccessState() {
        databinding.tilTitle.setErrorEnabled(false);
    }

    private void bindView() {
        databinding = ComponentCustomEditTextBinding.inflate(LayoutInflater.from(getContext()), this, true);
    }

    public void setListener(CustomListener l) {
        this.listener = l;
    }

    public String getText() {
        return databinding.tilTitle.getEditText().getText().toString();
    }

    public void setText(String text) {
        databinding.etTitle.setText(text);
    }

    public interface CustomListener {
        void onItemClick();
    }
}
