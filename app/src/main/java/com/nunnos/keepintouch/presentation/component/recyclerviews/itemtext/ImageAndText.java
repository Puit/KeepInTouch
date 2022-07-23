package com.nunnos.keepintouch.presentation.component.recyclerviews.itemtext;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class ImageAndText {
    private Drawable drawable;
    private Bitmap bitmap;
    private String text;

    public ImageAndText(Drawable drawable, String text) {
        this.drawable = drawable;
        this.text = text;
    }

    public ImageAndText(Bitmap bitmap, String text) {
        this.bitmap = bitmap;
        this.text = text;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
