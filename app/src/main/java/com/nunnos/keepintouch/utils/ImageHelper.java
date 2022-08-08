package com.nunnos.keepintouch.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageHelper {
    public static void resizeImage(ImageView imageView, Bitmap bm) {
        if (bm == null) return;

        float width = bm.getWidth();
        float height = bm.getHeight();

        float proporcion = (height > width) ? height / width : width / height;
        imageView.setScaleY(proporcion);
        imageView.setScaleX(proporcion);

    }
}
