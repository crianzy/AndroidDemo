package com.zuimeia.watcherinternetdata;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        watcherData();
    }

    private void watcherData() {
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_PERMISSIONS);
        for (PackageInfo info : packageInfos) {
            String[] permission = info.requestedPermissions;
            if (permission != null && permission.length > 0) {
                //找出需要网络服务的应用程序
                for (String premission : permission) {
                    if ("android.permission.INTERNET".equals(premission)) {
                        //获取每个应用程序在操作系统内的进程id
                        int uId = info.applicationInfo.uid;
                        //如果返回-1，代表不支持使用该方法，注意必须是2.2以上的
                        long rx = TrafficStats.getUidRxBytes(uId);
                        //如果返回-1，代表不支持使用该方法，注意必须是2.2以上的
                        long tx = TrafficStats.getUidTxBytes(uId);
                        Log.e(TAG, "watcherData: info.applicationInfo "
                                + info.applicationInfo.loadLabel(packageManager)
                                + " -- rx = " + Formatter.formatFileSize(this, rx)
                                + "  , tx = " + Formatter.formatFileSize(this, tx));
                    }
                }
            }

        }
    }
}
