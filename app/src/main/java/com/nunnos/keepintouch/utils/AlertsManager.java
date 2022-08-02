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
                    if(closeOnRight){
                        dialog.cancel();
                    }
                })
                .setNegativeButton(leftButton, (dialog, id) -> {
                    listener.onLeftClick();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public interface TwoButtonsAlertListener {
        void onLeftClick();

        void onRightClick();
    }
}
