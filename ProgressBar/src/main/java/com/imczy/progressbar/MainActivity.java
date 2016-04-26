package com.imczy.progressbar;

import android.animation.ValueAnimator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = "MainActivity";

	ProgressBar mProgressbar;
	private Handler mHandler = new Handler();

	ValueAnimator mValueAnimator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mProgressbar = (ProgressBar) findViewById(R.id.pb_progressbar);
		Log.e(TAG, "onCreate: " + getFilesDir().getPath());
//        mHandler.postDelayed(new Runnable() {
//            @Override`
//            public void run() {
//                mProgressbar.setProgress(20);
//            }
//        }, 3000);

		new Thread(new Runnable() {
			@Override
			public void run() {
				mProgressbar.setProgress(90);
			}
		}).start();
	}
}
