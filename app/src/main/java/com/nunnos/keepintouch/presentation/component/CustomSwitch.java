package com.nunnos.keepintouch.presentation.component;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.databinding.ComponentCustomSwitchBinding;

public class CustomSwitch extends ConstraintLayout {

    ComponentCustomSwitchBinding databinding;
    private boolean isRightClicked = true;
    private int selectedBackgroundColor;
    private int unselectedBackgroundColor;
    private int selectedTextColor;
    private int unselectedTextColor;
    private CustomListener listener;

    private String leftText, rightText;
    private Drawable leftImage, rightImage;

    public CustomSwitch(Context context) {
        this(context, null);
    }

    public CustomSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        bindView();
        setListeners();
        parseAttributes(attrs);
    }

    private void parseAttributes(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomSwitch, 0, 0);
            leftImage = a.getDrawable(R.styleable.CustomSwitch_custom_switch_image_left);
            rightImage = a.getDrawable(R.styleable.CustomSwitch_custom_switch_image_right);
            leftText = a.getString(R.styleable.CustomSwitch_custom_switch_text_left);
            rightText = a.getString(R.styleable.CustomSwitch_custom_switch_text_right);
            isRightClicked = a.getBoolean(R.styleable.CustomSwitch_custom_switch_checked_right, false);
            selectedBackgroundColor = a.getColor(R.styleable.CustomSwitch_custom_switch_selected_background_color, getContext().getColor(R.color.background_purple));
            unselectedBackgroundColor = a.getColor(R.styleable.CustomSwitch_custom_switch_unselected_background_color, getContext().getColor(R.color.white));
            selectedTextColor = a.getColor(R.styleable.CustomSwitch_custom_switch_selected_text_color, getContext().getColor(R.color.white));
            unselectedTextColor = a.getColor(R.styleable.CustomSwitch_custom_switch_unselected_text_color, getContext().getColor(R.color.black));
        }
        initView();
    }

    private void bindView() {
        databinding = ComponentCustomSwitchBinding.inflate(LayoutInflater.from(getContext()), this, true);
    }

    private void initView() {
        updateView();
        setListeners();
    }

    private void updateView() {
        setRightText(rightText);
        setLeftText(leftText);
        setLeftImage(leftImage);
        setRightImage(rightImage);
        if (isRightClicked) {
            checkRightButton();
        } else {
            checkLeftButton();
        }
    }

    public void checkRightButton() {
        databinding.customSwitchRadioGroup.check(databinding.customSwitchRight.getId());
        databinding.customSwitchLeft.setBackgroundColor(unselectedBackgroundColor);
        databinding.customSwitchRight.setBackgroundColor(selectedBackgroundColor);
        databinding.customSwitchTextLeft.setTextColor(unselectedTextColor);
        databinding.customSwitchTextRight.setTextColor(selectedTextColor);
        isRightClicked = true;
    }

    public void checkLeftButton() {
        databinding.customSwitchRadioGroup.check(databinding.customSwitchLeft.getId());
        databinding.customSwitchLeft.setBackgroundColor(selectedBackgroundColor);
        databinding.customSwitchRight.setBackgroundColor(unselectedBackgroundColor);
        databinding.customSwitchTextLeft.setTextColor(selectedTextColor);
        databinding.customSwitchTextRight.setTextColor(unselectedTextColor);
        isRightClicked = false;
    }

    public void setLeftImage(Drawable source) {
        if (source == null) return;
        databinding.customSwitchImageLeft.setImageDrawable(source);
        databinding.customSwitchImageLeft.setVisibility(VISIBLE);
        setTextAligment(databinding.customSwitchTextLeft, databinding.customSwitchImageLeft);
    }

    public void setRightImage(Drawable source) {
        if (source == null) return;
        databinding.customSwitchImageRight.setImageDrawable(source);
        databinding.customSwitchImageRight.setVisibility(VISIBLE);
        setTextAligment(databinding.customSwitchTextRight, databinding.customSwitchImageRight);
    }

    public void setLeftText(String text) {
        databinding.customSwitchTextLeft.setText(text);
        databinding.customSwitchTextLeft.setVisibility(VISIBLE);
        setTextAligment(databinding.customSwitchTextLeft, databinding.customSwitchImageLeft);
    }

    public void setRightText(String text) {
        databinding.customSwitchTextRight.setText(text);
        databinding.customSwitchTextRight.setVisibility(VISIBLE);
        setTextAligment(databinding.customSwitchTextRight, databinding.customSwitchImageRight);
    }

    private void setTextAligment(TextView tv, ImageView img) {
        if (img.getVisibility() == GONE) {
            tv.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        } else {
            tv.setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
        }
    }

    private void setListeners() {
        databinding.customSwitchRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (listener != null) {
                if (!isRightClicked) {
                    listener.onRight();
                    checkRightButton();
                } else {
                    listener.onLeft();
                    checkLeftButton();
                }
            }
        });
    }

    public boolean getIsRightClicked() {
        return isRightClicked;
    }

    public void setIsRightClicked(boolean isRightClicked) {
        if (isRightClicked) {
            checkRightButton();
        } else {
            checkLeftButton();
        }
    }

    public void callOnLeftClick() {
        listener.onLeft();
        isRightClicked = false;
    }

    public void setListener(CustomListener customListener) {
        listener = customListener;
    }

    public CustomListener getListener() {
        return listener;
    }

    public interface CustomListener {
        void onLeft();

        void onRight();
    }
}
