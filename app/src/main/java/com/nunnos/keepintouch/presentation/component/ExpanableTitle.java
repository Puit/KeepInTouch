package com.nunnos.keepintouch.presentation.component;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.databinding.ComponentExpanableTitleBinding;

public class ExpanableTitle extends ConstraintLayout {

    ComponentExpanableTitleBinding databinding;
    private CustomListener listener;
    private int errorColor;
    private int successColor;
    private Drawable icon;
    private String errorText;

    public ExpanableTitle(@NonNull Context context) {
        this(context, null);
    }

    public ExpanableTitle(@NonNull Context context, @Nullable AttributeSet attrs) {
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

        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.ExpanableTitle, 0, 0);
        String text = attributes.getString(R.styleable.ExpanableTitle_expanable_title_text);
        String hintText = attributes.getString(R.styleable.ExpanableTitle_expanable_title_hint_text);
        errorText = attributes.getString(R.styleable.ExpanableTitle_expanable_title_error_text);
        int textColor = attributes.getColor(R.styleable.ExpanableTitle_expanable_title_text_color, getContext().getColor(R.color.black));
        int hintTextColorAtEndPosition = attributes.getColor(R.styleable.ExpanableTitle_expanable_title_hint_text_color_at_end_position, getContext().getColor(R.color.background_gray_middle));
        int hintTextColorAtStartPosition = attributes.getColor(R.styleable.ExpanableTitle_expanable_title_hint_text_color_at_start_position, getContext().getColor(R.color.background_gray_dark));
        int underLineColor = attributes.getColor(R.styleable.ExpanableTitle_expanable_title_underline_color, getContext().getColor(R.color.background_purple));
        Drawable icon = attributes.getDrawable(R.styleable.ExpanableTitle_expanable_title_image_source);
        int iconColor = attributes.getColor(R.styleable.ExpanableTitle_expanable_title_image_color, Color.BLACK);
        boolean focusable = attributes.getBoolean(R.styleable.ExpanableTitle_expanable_title_focusable, true);
        if (!TextUtils.isEmpty(hintText)) {
            databinding.etHint.setText(hintText);
        }
        if (!TextUtils.isEmpty(text)) {
            databinding.etTitle.setText(text);
        }
        databinding.etTitle.setTextColor(textColor);
        databinding.etHint.setHintTextColor(ColorStateList.valueOf(hintTextColorAtEndPosition));
        if (icon != null) {
            this.icon = icon;
            databinding.etImage.setImageDrawable(tint(icon, iconColor));
        }
        if (!focusable){
            listener = null;
            databinding.etHint.setFocusable(false);
            databinding.etImage.setFocusable(false);
            databinding.etTitle.setFocusable(false);
            databinding.etHint.setClickable(false);
            databinding.etImage.setClickable(false);
            databinding.etTitle.setClickable(false);
        }

    }
    public void setListener(CustomListener l) {
        this.listener = l;
    }

    private void bindView() {
        databinding = ComponentExpanableTitleBinding.inflate(LayoutInflater.from(getContext()), this, true);
    }

    public String getText() {
        return databinding.etTitle.getText().toString();
    }

    public void setText(String text) {
        databinding.etTitle.setText(text);
    }
    public void setHint(String hint) {
        databinding.etHint.setHint(hint);
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
        databinding.etImage.setImageDrawable(icon);
    }

    public Drawable tint(Drawable drawable, int color) {

        drawable = drawable.mutate();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);

        return drawable;
    }

    public interface CustomListener {
        void onItemClick();
    }

}