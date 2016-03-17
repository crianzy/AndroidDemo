package com.zuimeia.activitymodel;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 15/12/29.
 */
public class MyApplication extends Application {
    public static final String TAG = "Activity";

    private Handler mHandler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                LogUtil.e(TAG, "postDelayed run 3000 ");
//                Intent intent = new Intent(getApplicationContext(), ActivityB.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            }
//        }, 3000);
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                LogUtil.e(TAG, "postDelayed run 10000");
//                Intent intent = new Intent(getApplicationContext(), ActivityB.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            }
//        }, 10000);
    }
}
