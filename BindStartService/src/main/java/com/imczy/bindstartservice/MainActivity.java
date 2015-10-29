package com.imczy.bindstartservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.imczy.common_util.log.LogUtil;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private Button mStartButton;
    private Button mStopButton;
    private Button mBindButton;
    private Button mUnBindButton;
    Intent intent;

    MyService mMyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartButton = (Button) findViewById(R.id.start_btn);
        mStopButton = (Button) findViewById(R.id.sto_btn);
        mBindButton = (Button) findViewById(R.id.bind_btn);
        mUnBindButton = (Button) findViewById(R.id.unbind_btn);
        intent = new Intent(MainActivity.this, MyService.class);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
            }
        });
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });
        mBindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
            }
        });
        mUnBindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(mServiceConnection);
            }
        });
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.d(TAG, "onServiceConnected");
            if (service != null) {
                mMyService = ((MyService.ServiceBinder) service).getService();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.d(TAG, "onServiceConnected");
            mMyService = null;
        }
    };
}
