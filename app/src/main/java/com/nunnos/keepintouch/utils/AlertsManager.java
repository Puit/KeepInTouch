package com.nunnos.keepintouch.utils;

import android.app.Activity;
import android.app.AlertDialog;

public class AlertsManager {
    public static void showTwoButtonsAlert(Activity activity, TwoButtonsAlertListener listener,
                                           String message, String leftButton, String rightButton, boolean closeOnRight) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(rightButton, (dialog, id) -> {
                    listener.onRightClick();
                    if (closeOnRight) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton(leftButton, (dialog, id) -> {
                    listener.onLeftClick();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showOneButtonAlert(Activity activity, OneButtonAlertListener listener,
                                          String message, String buttonText, boolean closeOnClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(buttonText, (dialog, id) -> {
                    if (listener != null) {
                        listener.onClick();
                    }
                    if (closeOnClick) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public interface TwoButtonsAlertListener {
        void onLeftClick();

        void onRightClick();
    }

    public interface OneButtonAlertListener {
        void onClick();
    }
}
