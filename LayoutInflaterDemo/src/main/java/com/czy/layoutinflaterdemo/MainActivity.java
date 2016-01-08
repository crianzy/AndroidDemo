package com.czy.layoutinflaterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewGroup = (RelativeLayout) findViewById(R.id.box);

        View view = null;
        view = LayoutInflater.from(this).inflate(R.layout.demo, mViewGroup, false);
        mViewGroup.addView(view);
    }
}
