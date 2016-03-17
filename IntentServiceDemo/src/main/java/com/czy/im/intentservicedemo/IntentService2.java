package com.czy.im.intentservicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by chenzhiyong on 16/3/16.
 */
public class IntentService2 extends IntentService {
    private static final String TAG = "IntentService2";

    public IntentService2() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.w(TAG, "onHandleIntent: IntentService2");
    }
}
