package com.nunnos.keepintouch.presentation.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.databinding.ComponentBottomMenuBinding;

public class BottomMenu extends ConstraintLayout {

    private ComponentBottomMenuBinding databinding;
    private ButtonsListeners listener;
    private Drawable unselectedImage1;
    private Drawable unselectedImage2;
    private Drawable unselectedImage3;
    private Drawable unselectedImage4;
    private Drawable selectedImage1;
    private Drawable selectedImage2;
    private Drawable selectedImage3;
    private Drawable selectedImage4;
    private int itemSelected = 0;

    public BottomMenu(@NonNull Context context) {
        this(context, null);
    }

    public BottomMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        parseAttributes(attrs);
    }

    private void bindView() {
        databinding = ComponentBottomMenuBinding.inflate(LayoutInflater.from(getContext()), this, true);
    }

    private void init() {
        bindView();
        initView();
    }

    private void initView() {
        databinding.bottomMenuImage1.setOnClickListener(v -> {
            if (listener != null && itemSelected != 1) {
                updateButtons(1);
                listener.onButton1Click();
            }
        });
        databinding.bottomMenuImage2.setOnClickListener(v -> {
            if (listener != null && itemSelected != 2) {
                updateButtons(2);
                listener.onButton2Click();
            }
        });
        databinding.bottomMenuImage3.setOnClickListener(v -> {
            if (listener != null && itemSelected != 3) {
                updateButtons(3);
                listener.onButton3Click();
            }
        });
        databinding.bottomMenuImage4.setOnClickListener(v -> {
            if (listener != null && itemSelected != 4) {
                updateButtons(4);
                listener.onButton4Click();
            }
        });
    }
    public void setItemSelected(int index){
        itemSelected = index;
        updateButtons(index);
    }

    private void updateButtons(int buttonIndex) {
        itemSelected = buttonIndex;
        setAllButtonsUnselected();
        switch (buttonIndex) {
            case 1:
                databinding.bottomMenuImage1.setImageDrawable(selectedImage1);
                break;
            case 2:
                databinding.bottomMenuImage2.setImageDrawable(selectedImage2);
                break;
            case 3:
                databinding.bottomMenuImage3.setImageDrawable(selectedImage3);
                break;
            case 4:
                databinding.bottomMenuImage4.setImageDrawable(selectedImage4);
                break;
        }
    }

    private void setAllButtonsUnselected() {
        databinding.bottomMenuImage1.setImageDrawable(unselectedImage1);
        databinding.bottomMenuImage2.setImageDrawable(unselectedImage2);
        databinding.bottomMenuImage3.setImageDrawable(unselectedImage3);
        databinding.bottomMenuImage4.setImageDrawable(unselectedImage4);
    }

    public int getItemSelected() {
        return itemSelected;
    }

    public void setListener(ButtonsListeners listener) {
        this.listener = listener;
    }

    private void parseAttributes(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.BottomMenuComponent, 0, 0);
        Drawable unselectedImage1 = attributes.getDrawable(R.styleable.BottomMenuComponent_image1_unselected);
        Drawable unselectedImage2 = attributes.getDrawable(R.styleable.BottomMenuComponent_image2_unselected);
        Drawable unselectedImage3 = attributes.getDrawable(R.styleable.BottomMenuComponent_image3_unselected);
        Drawable unselectedImage4 = attributes.getDrawable(R.styleable.BottomMenuComponent_image4_unselected);
        Drawable selectedImage1 = attributes.getDrawable(R.styleable.BottomMenuComponent_image1_selected);
        Drawable selectedImage2 = attributes.getDrawable(R.styleable.BottomMenuComponent_image2_selected);
        Drawable selectedImage3 = attributes.getDrawable(R.styleable.BottomMenuComponent_image3_selected);
        Drawable selectedImage4 = attributes.getDrawable(R.styleable.BottomMenuComponent_image4_selected);
        int colorSelected = attributes.getColor(R.styleable.BottomMenuComponent_color_selected, Color.BLACK);
        int colorUnselected = attributes.getColor(R.styleable.BottomMenuComponent_color_unselected, Color.GRAY);
        int colorDividers = attributes.getColor(R.styleable.BottomMenuComponent_color_dividers, Color.GRAY);

        itemSelected = attributes.getIndex(R.styleable.BottomMenuComponent_item_selected);

        if (unselectedImage1 != null) {
            this.unselectedImage1 = unselectedImage1;
            databinding.bottomMenuImage1.setImageDrawable(tint(unselectedImage1, colorUnselected));
        }
        if (unselectedImage2 != null) {
            this.unselectedImage2 = unselectedImage2;
            databinding.bottomMenuImage2.setImageDrawable(tint(unselectedImage2, colorUnselected));
        }
        if (unselectedImage3 != null) {
            this.unselectedImage3 = unselectedImage3;
            databinding.bottomMenuImage3.setImageDrawable(tint(unselectedImage3, colorUnselected));
        }
        if (unselectedImage4 != null) {
            this.unselectedImage4 = unselectedImage4;
            databinding.bottomMenuImage4.setImageDrawable(tint(unselectedImage4, colorUnselected));
        }
        if (selectedImage1 != null) {
            this.selectedImage1 = tint(selectedImage1, colorSelected);
        }
        if (selectedImage2 != null) {
            this.selectedImage2 = tint(selectedImage2, colorSelected);
        }
        if (selectedImage3 != null) {
            this.selectedImage3 = tint(selectedImage3, colorSelected);
        }
        if (selectedImage4 != null) {
            this.selectedImage4 = tint(selectedImage4, colorSelected);
        }
        switch (itemSelected) {
            case 1:
                databinding.bottomMenuImage1.setImageDrawable(this.selectedImage1);
                break;
            case 2:
                databinding.bottomMenuImage2.setImageDrawable(this.selectedImage2);
                break;
            case 3:
                databinding.bottomMenuImage3.setImageDrawable(this.selectedImage3);
                break;
            case 4:
                databinding.bottomMenuImage4.setImageDrawable(this.selectedImage4);
                break;
            default:
                //No item selected
        }

        if (selectedImage1 == null || unselectedImage1 == null) {
            databinding.bottomMenuImage1.setVisibility(GONE);
        }
        if (selectedImage2 == null || unselectedImage2 == null) {
            databinding.bottomMenuImage2.setVisibility(GONE);
        }
        if (selectedImage3 == null || unselectedImage3 == null) {
            databinding.bottomMenuImage3.setVisibility(GONE);
        }
        if (selectedImage4 == null || unselectedImage4 == null) {
            databinding.bottomMenuImage4.setVisibility(GONE);
        }

    }

    public Drawable tint(Drawable drawable, int color) {

        drawable = drawable.mutate();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);

        return drawable;
    }

    public interface ButtonsListeners {
        void onButton1Click();

        void onButton2Click();

        void onButton3Click();

        void onButton4Click();
    }

}
