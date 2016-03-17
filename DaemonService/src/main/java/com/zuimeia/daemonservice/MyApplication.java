package com.zuimeia.daemonservice;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

/**
 * Created by chenzhiyong on 16/3/4.
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
        getApplicationContext().startService(new Intent(this, DeamonService.class));
    }
}
