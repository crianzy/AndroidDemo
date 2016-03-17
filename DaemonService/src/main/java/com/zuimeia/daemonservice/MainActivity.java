package com.zuimeia.daemonservice;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String cmd = getDir("bin", Context.MODE_PRIVATE).getAbsolutePath() + File.separator + "libdm.so";
        String cmd = "/data/data/com.zuimeia.daemonservice/lib/libdm.so 1 com.zuimeia.daemonservice 100 1.0.0";
        Log.e(TAG, "onCreate: cmd = " + cmd);
        DaemonUtil.RunCommand(cmd, "");
    }

}
