package com.imczy.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 15/10/27.
 */
public class MainService extends Service {
    public static final String TAG = "MainService";

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d(TAG, "onBind");
        return mBinder;
    }

    private IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
        }

        @Override
        public int getPid() throws RemoteException {
            // 返回当前 service 的pic
            LogUtil.d(TAG, "getPid");
            return android.os.Process.myPid();
        }
    };

}
