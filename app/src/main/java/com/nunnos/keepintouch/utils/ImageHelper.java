package com.nunnos.keepintouch.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
