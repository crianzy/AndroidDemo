package com.zuimeia.viewmeasurelayoutdrawdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.imczy.common_util.log.LogUtil;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView = findViewById(R.id.my_view);
        LogUtil.d(TAG, "onCreate before measure mView = " + mView.getMeasuredWidth() + " , height = " + mView.getMeasuredHeight());

        mView.measure(0, 0);
        LogUtil.d(TAG, "onCreate after measure mView = " + mView.getMeasuredWidth() + " , height = " + mView.getMeasuredHeight());

    }
}
