package com.zuimeia.android5activityanimator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeImageTransform;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
    }

    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }
}
