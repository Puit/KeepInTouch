package com.nunnos.keepintouch.presentation.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.databinding.ComponentCustomTextViewBinding;

public class CustomTextView extends ConstraintLayout {
    ComponentCustomTextViewBinding databinding;

    public CustomTextView(@NonNull Context context) {
        this(context, null);
    }

    public CustomTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bindView();
        parseAttributes(attrs);
    }

    private void parseAttributes(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView, 0, 0);
        String text = attributes.getString(R.styleable.CustomTextView_text_view_text);
        String hintText = attributes.getString(R.styleable.CustomTextView_text_view_hint_text);
        int textColor = attributes.getColor(R.styleable.CustomTextView_text_view_text_color, getContext().getColor(R.color.black));
        int underLineColor = attributes.getColor(R.styleable.CustomTextView_text_view_underline_color, getContext().getColor(R.color.background_purple));
        if (!TextUtils.isEmpty(hintText)) {
            databinding.tilTitle.setHint(hintText);
        }
        if (!TextUtils.isEmpty(text)) {
            databinding.tvTitle.setText(text);
        }
        databinding.tvTitle.setTextColor(textColor);
        databinding.tvUnderLine.setBackgroundColor(underLineColor);

    }

    private void bindView() {
        databinding = ComponentCustomTextViewBinding.inflate(LayoutInflater.from(getContext()), this, true);
    }

    public String getText() {
        return databinding.tvTitle.getText().toString();
    }

    public void setText(String text) {
        databinding.tvTitle.setText(text);
    }

    public String getTitle() {
        return databinding.tilTitle.getText().toString();
    }

    public void setTitle(String title) {
        databinding.tilTitle.setText(title);
    }

    public void setImage(int src) {
        databinding.image.setVisibility(VISIBLE);
        databinding.image.setImageResource(src);
    }

}