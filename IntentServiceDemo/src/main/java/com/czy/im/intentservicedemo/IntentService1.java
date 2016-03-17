package com.czy.im.intentservicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by chenzhiyong on 16/3/16.
 */
public class IntentService1 extends IntentService {
    private static final String TAG = "IntentService1";

    public IntentService1() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Log.e(TAG, "onHandleIntent: IntentService1 ");
            Thread.sleep(3000);
            Log.e(TAG, "onHandleIntent: IntentService1 -------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
