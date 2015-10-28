package com.imczy.aidldemo;

import android.app.Application;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 15/10/27.
 */
public class MyApplication extends Application {
    public static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        // 获取当前进程 id
        LogUtil.d(TAG, "pid = " + android.os.Process.myPid());
    }


}
