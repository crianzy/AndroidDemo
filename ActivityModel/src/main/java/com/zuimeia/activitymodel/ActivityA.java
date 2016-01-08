package com.zuimeia.activitymodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 15/11/4.
 */
public class ActivityA extends Activity {
    public static final String TAG = "Activity";

    private TextView mTextView;
    private Button mButton;

    private Handler mHandler = new Handler();


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.d(TAG, "ActivityA onNewIntent");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtil.d(TAG, "ActivityA onCreate taskId = " + getTaskId());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.txt);
        mButton = (Button) findViewById(R.id.btn);

        Log.d(TAG, "onCreate: " + this.toString() + ", taskId=" + this.getTaskId());
        finish();

        mTextView.setText(getClass().getSimpleName());
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContxt(), ActivityB.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                getApplicationContext().startActivity(intent);

//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        LogUtil.e(TAG, "postDelayed run");
//                        Intent intent = new Intent(getContxt(), ActivityB.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                    }
//                }, 8000);
            }
        });

        finish();
    }

    public Context getContxt() {
        return this;
    }
}
