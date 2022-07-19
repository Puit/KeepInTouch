package com.nunnos.keepintouch.base.baseview.base;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;


public class App extends Application {
    public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL";
    private static Context context;
    private static Application sApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        createNotificationChannnel();
    }

    private void createNotificationChannnel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "WeatherAlarm Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }


    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() { // TODO: MIRAR; SEMBLA QUE NO VA BÉ
        return getApplication().getApplicationContext();
    }

}
