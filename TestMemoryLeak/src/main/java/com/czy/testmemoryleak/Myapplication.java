package com.czy.testmemoryleak;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by chenzhiyong on 15/12/17.
 */
public class Myapplication extends Application {

    public static RefWatcher getRefWatcher(Context context) {
        Myapplication application = (Myapplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
    }
}
