package com.czy.intentflag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityB extends AppCompatActivity {
	private static final String TAG = "ActivityB";

	TextView mTextView;

	Button btn1;
	Button btn2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTextView = (TextView) findViewById(R.id.txt);
		Log.e(TAG, "ActivityB onCreate taskId = " + getTaskId());
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);

		mTextView.setText("BBB");
		btn1.setText("normal");

		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActivityB.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}
}
