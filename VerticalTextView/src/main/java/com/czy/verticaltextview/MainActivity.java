package com.czy.verticaltextview;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	TextViewWrap mTextViewWrap;
	TextView mTextView;

	Button mShowBtn;
	Button mHiddenBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTextViewWrap = (TextViewWrap) findViewById(R.id.txt_wrap_layout);
		mTextViewWrap.setText("在应用开发中，大家会遇到一个问题");

		mTextView = (TextView) findViewById(R.id.txt);
		mShowBtn = (Button) findViewById(R.id.show_btn);
		mHiddenBtn = (Button) findViewById(R.id.hidden_btn);


		mShowBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTextViewWrap.show();
			}
		});

		mHiddenBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTextViewWrap.hidden2();
			}
		});

		mTextView.post(new Runnable() {
			@Override
			public void run() {
				mTextView.setPivotX(mTextView.getWidth());
				mTextView.setPivotY(mTextView.getHeight() / 2);
			}
		});

	}

	private void show() {
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mTextView, "rotationY", 90, 0);
		objectAnimator.setDuration(600);
		objectAnimator.start();
	}

	private void hidden() {
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mTextView, "rotationY", mTextView.getRotationY(), 90);
		objectAnimator.setDuration(600);
		objectAnimator.start();
	}
}
