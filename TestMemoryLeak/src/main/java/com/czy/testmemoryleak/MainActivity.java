package com.czy.testmemoryleak;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.squareup.leakcanary.RefWatcher;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTextView.setText("hello");
            }
        }, 900000000l);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = Myapplication.getRefWatcher(this);
        refWatcher.watch(this);
    }
}
