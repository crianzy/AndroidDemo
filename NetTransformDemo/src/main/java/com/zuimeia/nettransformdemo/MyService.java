package com.zuimeia.nettransformdemo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.imczy.common_util.log.LogUtil;

import java.io.IOException;

/**
 * Created by chenzhiyong on 16/3/6.
 */
public class MyService extends Service {
    private static final String TAG = "MyService";

    HelloServer mHelloServer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d(TAG, "onStartCommand");
        startNanoHttpServer();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startNanoHttpServer() {
        mHelloServer = new HelloServer(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取wifi服务
                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                //判断wifi是否开启
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                String ip = formatIpAddress(ipAddress);
                LogUtil.e(TAG, "ip = " + ip);
                try {
                    mHelloServer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private static String formatIpAddress(int ipAdress) {

        return (ipAdress & 0xFF) + "." +
                ((ipAdress >> 8) & 0xFF) + "." +
                ((ipAdress >> 16) & 0xFF) + "." +
                (ipAdress >> 24 & 0xFF);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHelloServer.stop();
    }
}
