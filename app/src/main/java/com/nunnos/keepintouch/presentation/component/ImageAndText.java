package com.nunnos.keepintouch.presentation.component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.nunnos.keepintouch.databinding.ComponentImageAndTextBinding;

public class ImageAndText extends ConstraintLayout {
    ComponentImageAndTextBinding databinding;
    private CustomListener listener;

    public ImageAndText(@NonNull Context context) {
        this(context, null);
    }

    public ImageAndText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bindView();
        setListener();
    }

    private void bindView() {
        databinding = ComponentImageAndTextBinding.inflate(LayoutInflater.from(getContext()), this, true);

    }

    private void setListener() {
        this.setOnClickListener(v -> listener.onItemClick());
    }

    public void setListener(CustomListener l) {
        this.listener = l;
    }

    public String getText() {
        return databinding.imageAndTextText.getText().toString();
    }

    public void setText(String text) {
        databinding.imageAndTextText.setText(text);
    }

    public void setImage(int src) {
        databinding.imageAndTextImage.setImageResource(src);
    }

    public Drawable getImageSRC() {
        return databinding.imageAndTextImage.getDrawable();
    }

    public interface CustomListener {
        void onItemClick();
    }
}
