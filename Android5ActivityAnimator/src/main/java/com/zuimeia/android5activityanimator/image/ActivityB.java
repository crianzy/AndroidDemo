package com.zuimeia.android5activityanimator.image;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zuimeia.android5activityanimator.R;

/**
 * Created by chenzhiyong on 16/1/12.
 */
public class ActivityB extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSharedElementEnterTransition(new MyAutoTransFrom(this));
        getWindow().setSharedElementExitTransition(new MyAutoTransFrom(this));
        getWindow().setSharedElementReenterTransition(new MyAutoTransFrom(this));
        getWindow().setSharedElementReturnTransition(new MyAutoTransFrom(this));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
    }

    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }
}
